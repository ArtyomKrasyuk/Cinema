package com.example.OrderService.controllers;

import com.example.OrderService.dto.PaymentRequestDTO;
import com.example.OrderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<?> pay(@RequestBody PaymentRequestDTO dto){
        orderService.pay(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refund/{orderId}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<?> refund(@PathVariable long orderId){
        orderService.refund(orderId);
        return ResponseEntity.ok().build();
    }
}
