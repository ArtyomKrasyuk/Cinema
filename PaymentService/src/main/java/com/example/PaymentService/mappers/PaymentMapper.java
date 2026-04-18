package com.example.PaymentService.mappers;

import com.example.PaymentService.events.ProcessPaymentEvent;
import com.example.PaymentService.models.Payment;
import com.example.PaymentService.repos.PaymentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    @Autowired
    protected PaymentRepository paymentRepository;

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    public abstract Payment toEntity(ProcessPaymentEvent event);
}
