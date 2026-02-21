package com.example.OrderService.repos;

import com.example.OrderService.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query("""
        UPDATE Order o
        SET o.state = 'PROCESSING',
            o.expiresAt = null
        WHERE o.orderId = :id
          AND o.state = 'CREATED'
          AND o.expiresAt > CURRENT_TIMESTAMP
    """)
    int moveToProcessing(@Param("id") Long id);
}
