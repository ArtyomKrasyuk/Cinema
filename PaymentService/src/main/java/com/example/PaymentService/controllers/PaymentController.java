package com.example.PaymentService.controllers;

import com.example.PaymentService.dto.PaymentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping
    public ResponseEntity<?> payment(@RequestBody PaymentDTO dto){
        try {
            Thread.sleep(500);
            return ResponseEntity.ok().build();
        } catch (InterruptedException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
