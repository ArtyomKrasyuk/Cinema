package com.example.PaymentService.handlers;

import com.example.PaymentService.events.*;
import com.example.PaymentService.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessPaymentEventHandler {
    @Value("${app.kafka.payment.succeeded.topic}")
    private String paymentSucceededTopic;
    @Value("${app.kafka.payment.failed.topic}")
    private String paymentFailedTopic;
    @Value("${app.kafka.refund.succeeded.topic}")
    private String refundSucceededTopic;
    @Value("${app.kafka.refund.failed.topic}")
    private String refundFailedTopic;
    private final KafkaTemplate<String, PaymentSucceededEvent> kafkaTemplateForSucceededPayment;
    private final KafkaTemplate<String, PaymentFailedEvent> kafkaTemplateForFailedPayment;
    private final KafkaTemplate<String, RefundSucceededEvent> kafkaTemplateForSucceededRefund;
    private final KafkaTemplate<String, RefundFailedEvent> kafkaTemplateForFailedRefund;
    private final PaymentService paymentService;

    @KafkaListener(
            topics = "${app.kafka.process.payment.topic}",
            containerFactory = "containerFactoryForProcessPayment"
    )
    public void handleProcessPaymentEvent(ProcessPaymentEvent event){
        try{
            if(paymentService.processPayment(event)){
                kafkaTemplateForSucceededPayment.send(paymentSucceededTopic, new PaymentSucceededEvent(event.orderId()));
            }
            else kafkaTemplateForFailedPayment.send(paymentFailedTopic, new PaymentFailedEvent(event.orderId()));
        }
        catch (RuntimeException ex){
            System.out.println(ex.getMessage());
            kafkaTemplateForFailedPayment.send(paymentFailedTopic, new PaymentFailedEvent(event.orderId()));
        }
    }

    @KafkaListener(
            topics = "${app.kafka.refund.event.topic}",
            containerFactory = "containerFactoryForRefund"
    )
    public void handleRefundEvent(RefundEvent event){
        try{
            if(paymentService.refundPayment(event.orderId())){
                kafkaTemplateForSucceededRefund.send(refundSucceededTopic, new RefundSucceededEvent(event.orderId()));
            }
            else kafkaTemplateForFailedRefund.send(refundFailedTopic, new RefundFailedEvent(event.orderId()));
        }
        catch (RuntimeException ex){
            System.out.println(ex.getMessage());
            kafkaTemplateForFailedRefund.send(refundFailedTopic, new RefundFailedEvent(event.orderId()));
        }
    }
}
