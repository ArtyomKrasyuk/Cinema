package com.example.OrderService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_seat")
@Data
@NoArgsConstructor
public class OrderSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_seat_id")
    private Long orderSeatId;
    @Column(name = "seat_id")
    private Long seatId;
    @Column(name = "seat_number")
    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
}
