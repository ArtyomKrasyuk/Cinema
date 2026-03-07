package com.example.OrderService.repos;

import com.example.OrderService.models.OrderSeat;
import com.example.OrderService.models.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSeatRepository extends JpaRepository<OrderSeat, Long> {

    @Modifying
    @Query("""
    UPDATE OrderSeat os
    SET os.state = :state
    WHERE os.order.orderId = :orderId
""")
    void updateStateByOrderId(Long orderId, OrderState state);

    @Modifying
    @Query("""
    UPDATE OrderSeat os
    SET os.state = :state
    WHERE os.order.orderId = :orderId
""")
    int moveSeatsToState(@Param("orderId") Long orderId, String state);

    @Modifying
    @Query("""
    UPDATE OrderSeat os
    SET os.state = 'EXPIRED'
    WHERE os.order.orderId IN :orderIds
      AND os.state = 'CREATED'
""")
    int expireSeats(@Param("orderIds") List<Long> orderIds);

    List<OrderSeat> findAllByShowtimeId(Long showtimeId);
}
