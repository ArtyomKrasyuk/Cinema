package com.example.OrderService.controllers;

import com.example.OrderService.dto.OrderCreatedDTO;
import com.example.OrderService.dto.OrderRequestDTO;
import com.example.OrderService.dto.OrderStatusResponseDTO;
import com.example.OrderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderCreatedDTO save(@RequestBody OrderRequestDTO dto){
        return orderService.save(dto);
    }

    @GetMapping("/status/{orderId}")
    public OrderStatusResponseDTO getStatus(@PathVariable long orderId){
        return orderService.getStatus(orderId);
    }
}
