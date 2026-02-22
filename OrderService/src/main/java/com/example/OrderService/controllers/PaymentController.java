package com.example.OrderService.controllers;

import com.example.OrderService.dto.PaymentRequestDTO;
import com.example.OrderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> pay(@RequestBody PaymentRequestDTO dto){
        orderService.pay(dto);
        return ResponseEntity.ok().build();
    }
}
