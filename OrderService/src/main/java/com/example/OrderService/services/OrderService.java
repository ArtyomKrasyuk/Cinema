package com.example.OrderService.services;

import com.example.OrderService.dto.OrderCreatedDTO;
import com.example.OrderService.dto.OrderRequestDTO;
import com.example.OrderService.dto.PaymentRequestDTO;
import com.example.OrderService.mappers.OrderMapper;
import com.example.OrderService.models.Order;
import com.example.OrderService.repos.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private OrderMapper orderMapper;
    private OrderRepository orderRepository;

    public OrderCreatedDTO save(OrderRequestDTO dto){
        Order order = orderMapper.toEntity(dto);
        Instant expires = Instant.now().plus(Duration.ofMinutes(15));
        order.setExpiresAt(Timestamp.from(expires));
        Order entity = orderRepository.save(order);
        return new OrderCreatedDTO(entity.getOrderId(), entity.getExpiresAt().toString(), entity.getPrice());
    }

    public void pay(PaymentRequestDTO dto){
        int updated = orderRepository.moveToProcessing(dto.orderId());
        if(updated == 0) throw new RuntimeException("Истекло время оплаты заказа");
        // TODO: Отправка ивента об оплате в кафку
    }
}
