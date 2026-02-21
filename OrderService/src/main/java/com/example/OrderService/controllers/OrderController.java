package com.example.OrderService.controllers;

import com.example.OrderService.dto.OrderCreatedDTO;
import com.example.OrderService.dto.OrderRequestDTO;
import com.example.OrderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping
    public OrderCreatedDTO save(@RequestBody OrderRequestDTO dto){
        return orderService.save(dto);
    }
}
