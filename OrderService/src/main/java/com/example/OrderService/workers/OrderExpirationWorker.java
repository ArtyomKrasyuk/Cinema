package com.example.OrderService.workers;

import com.example.OrderService.repos.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderExpirationWorker {

    private final OrderRepository repository;

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void expireOrders() {
        List<Long> expiredIds = repository.expireOrders();

        // TODO: Реализовать паттерн outbox
        if (!expiredIds.isEmpty()) expiredIds.forEach(id -> System.out.println("Истёк заказ с id: " + id));
    }
}
