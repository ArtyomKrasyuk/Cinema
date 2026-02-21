package com.example.OrderService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "client_id")
    private UUID clientId;
    @Column(name = "showtime_id")
    private Long showtimeId;
    @Column(name = "movie_title")
    private String movieTitle;
    @Column(name = "cinema_title")
    private String cinemaTitle;
    @Column(name = "hall_number")
    private Integer hallNumber;
    @Column(name = "time")
    private Timestamp time;
    @Column(name = "expires_at")
    private Timestamp expiresAt;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<OrderSeat> seats;
}
