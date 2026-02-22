package com.example.PaymentService.handlers;

import com.example.PaymentService.events.PaymentFailedEvent;
import com.example.PaymentService.events.PaymentSucceededEvent;
import com.example.PaymentService.events.ProcessPaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessPaymentEventHandler {
    @Value("app.kafka.payment.succeeded.topic")
    private String paymentSucceededTopic;
    @Value("app.kafka.payment.failed.topic")
    private String paymentFailedTopic;
    private final KafkaTemplate<String, PaymentSucceededEvent> kafkaTemplateForSucceededPayment;
    private final KafkaTemplate<String, PaymentFailedEvent> kafkaTemplateForFailedPayment;

    @KafkaListener(topics = "${app.kafka.process.payment.topic}")
    public void handleProcessPaymentEvent(ProcessPaymentEvent event){
        try {
            Thread.sleep(2000);
            kafkaTemplateForSucceededPayment.send(paymentSucceededTopic, new PaymentSucceededEvent(event.orderId()));
        } catch (InterruptedException e) {
            kafkaTemplateForFailedPayment.send(paymentFailedTopic, new PaymentFailedEvent(event.orderId()));
        }
    }
}
