package com.example.PaymentService.services;

import com.example.PaymentService.events.ProcessPaymentEvent;
import com.example.PaymentService.mappers.PaymentMapper;
import com.example.PaymentService.models.Payment;
import com.example.PaymentService.models.PaymentStatus;
import com.example.PaymentService.repos.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public boolean processPayment(ProcessPaymentEvent event) {
        Payment payment = paymentMapper.toEntity(event);
        var result = mockExternalPaymentGateway();
        if(result){
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            paymentRepository.save(payment);
            return true;
        }
        else return false;
    }

    @Transactional
    public boolean refundPayment(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order: " + orderId));
        if (payment.getPaymentStatus() != PaymentStatus.COMPLETED) {
            throw new RuntimeException("Cannot refund payment with status: " + payment.getPaymentStatus());
        }

        var result = mockExternalPaymentGateway();

        if(result){
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
            paymentRepository.save(payment);
            return true;
        }
        else return false;
    }

    private boolean mockExternalPaymentGateway() {
        try {
            Thread.sleep(2000);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }
}
