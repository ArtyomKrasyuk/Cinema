package com.example.OrderService.services;

import com.example.OrderService.dto.*;
import com.example.OrderService.events.PaymentFailedEvent;
import com.example.OrderService.events.PaymentSucceededEvent;
import com.example.OrderService.events.ProcessPaymentEvent;
import com.example.OrderService.events.RefundEvent;
import com.example.OrderService.mappers.OrderMapper;
import com.example.OrderService.mappers.OrderSeatMapper;
import com.example.OrderService.models.Order;
import com.example.OrderService.models.OrderState;
import com.example.OrderService.repos.OrderRepository;
import com.example.OrderService.repos.OrderSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Value("app.kafka.process.payment.topic")
    private String processPaymentTopic;
    @Value("app.kafka.refund.event.topic")
    private String refundTopic;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderSeatMapper orderSeatMapper;
    private final OrderSeatRepository orderSeatRepository;
    private final KafkaTemplate<String, ProcessPaymentEvent> kafkaTemplateForProcessPayment;
    private final KafkaTemplate<String, RefundEvent> kafkaTemplateForRefund;

    public OrderCreatedDTO save(OrderRequestDTO dto){
        Order order = orderMapper.toEntity(dto);
        Instant expires = Instant.now().plus(Duration.ofMinutes(15));
        order.setExpiresAt(Timestamp.from(expires));
        Order entity = orderRepository.save(order);
        orderSeatRepository.saveAll(
                dto.seats().stream().map(seat -> orderSeatMapper.toEntity(seat, entity)).toList()
        );
        return new OrderCreatedDTO(entity.getOrderId(), entity.getExpiresAt().toString(), entity.getPrice());
    }

    public void pay(PaymentRequestDTO dto){
        int updated = orderRepository.moveToProcessing(dto.orderId());
        if(updated == 0) throw new RuntimeException("Истекло время оплаты заказа");
        orderSeatRepository.moveSeatsToState(dto.orderId(), OrderState.PROCESSING.toString());
        Order order = orderRepository.findById(dto.orderId()).orElseThrow(
                () -> new RuntimeException("Не найден заказ с id: " + dto.orderId()));
        var event = new ProcessPaymentEvent(
                dto.cardNumber(),
                dto.expiration(),
                dto.code(),
                dto.orderId(),
                order.getPrice()
        );
        kafkaTemplateForProcessPayment.send(processPaymentTopic, event);
    }

    public void handleSuccess(PaymentSucceededEvent event){
        Order order = orderRepository.findById(event.orderId()).orElseThrow(
                () -> new RuntimeException("Не найден заказ с id: " + event.orderId()));
        order.setState(OrderState.CONFIRMED);
        orderRepository.save(order);
        orderSeatRepository.moveSeatsToState(event.orderId(), OrderState.CONFIRMED.toString());
    }

    public void handleFail(PaymentFailedEvent event){
        Order order = orderRepository.findById(event.orderId()).orElseThrow(
                () -> new RuntimeException("Не найден заказ с id: " + event.orderId()));
        order.setState(OrderState.PAYMENT_FAILED);
        orderRepository.save(order);
        orderSeatRepository.moveSeatsToState(event.orderId(), OrderState.PAYMENT_FAILED.toString());
    }

    public OrderStatusResponseDTO getStatus(long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Не найден заказ с id: " + orderId));
        return new OrderStatusResponseDTO(order.getState().toString());
    }

    public void refund(RefundRequestDTO dto){
        int updated = orderRepository.moveToCanceled(dto.orderId());
        if(updated == 0) throw new RuntimeException("Заказ нельзя отменить");
        orderSeatRepository.moveSeatsToState(dto.orderId(), OrderState.CANCELED.toString());
        kafkaTemplateForRefund.send(refundTopic, new RefundEvent(dto.orderId()));
    }
}
