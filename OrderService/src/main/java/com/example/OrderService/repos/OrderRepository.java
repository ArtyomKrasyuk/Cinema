package com.example.OrderService.repos;

import com.example.OrderService.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE orders
        SET state = 'EXPIRED',
            expires_at = NULL
        WHERE state = 'CREATED'
        AND expires_at <= now()
        RETURNING order_id
        """,
            nativeQuery = true)
    List<Long> expireOrders();

    @Modifying
    @Transactional
    @Query("""
        UPDATE Order o
        SET o.state = 'CANCELED'
        WHERE o.orderId = :id
          AND o.state = 'CONFIRMED'
          AND o.time > CURRENT_TIMESTAMP
    """)
    int moveToCanceled(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Order o
        SET o.state = 'REFUNDED'
        WHERE o.orderId = :id
          AND o.state = 'CANCELED'
    """)
    int moveToRefunded(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Order o
        SET o.state = 'CONFIRMED'
        WHERE o.orderId = :id
          AND o.state = 'CANCELED'
    """)
    int moveToConfirmed(@Param("id") Long id);

    List<Order> findAllByClientId(UUID clientId);
}
