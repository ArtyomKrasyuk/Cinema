package com.example.OrderService.handlers;

import com.example.OrderService.events.PaymentFailedEvent;
import com.example.OrderService.events.PaymentSucceededEvent;
import com.example.OrderService.events.RefundFailedEvent;
import com.example.OrderService.events.RefundSucceededEvent;
import com.example.OrderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentResultEventHandler {
    private final OrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.payment.succeeded.topic}",
            containerFactory = "containerFactoryForPaymentSucceeded"
    )
    public void handleSuccess(PaymentSucceededEvent event){
        orderService.handleSuccess(event);
    }

    @KafkaListener(
            topics = "${app.kafka.payment.failed.topic}",
            containerFactory = "containerFactoryForPaymentFailed"
    )
    public void handleFail(PaymentFailedEvent event){
        orderService.handleFail(event);
    }
    @KafkaListener(
            topics = "${app.kafka.refund.succeeded.topic}",
            containerFactory = "containerFactoryForRefundSucceeded"
    )
    public void handleRefundSuccess(RefundSucceededEvent event){
        orderService.handleRefundSuccess(event);
    }

    @KafkaListener(
            topics = "${app.kafka.refund.failed.topic}",
            containerFactory = "containerFactoryForRefundFailed"
    )
    public void handleRefundFail(RefundFailedEvent event){
        orderService.handleRefundFail(event);
    }
}
