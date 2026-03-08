package com.example.OrderService.controllers;

import com.example.OrderService.dto.*;
import com.example.OrderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('client')")
    public OrderCreatedDTO save(@RequestBody OrderRequestDTO dto, @AuthenticationPrincipal Jwt jwt){
        return orderService.save(dto, UUID.fromString(jwt.getSubject()));
    }

    @GetMapping
    @PreAuthorize("hasRole('client')")
    public List<OrderResponseDTO> getOrders(@AuthenticationPrincipal Jwt jwt){
        return orderService.getOrders(UUID.fromString(jwt.getSubject()));
    }

    @GetMapping("/status/{orderId}")
    @PreAuthorize("hasRole('client')")
    public OrderStatusResponseDTO getStatus(@PathVariable long orderId){
        return orderService.getStatus(orderId);
    }

    @GetMapping("/reserved/{showtimeId}")
    public ReservedSeatsResponseDTO getReserverSeats(@PathVariable long showtimeId){
        return orderService.getReservedSeats(showtimeId);
    }
}
