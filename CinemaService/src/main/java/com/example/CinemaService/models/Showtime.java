package com.example.CinemaService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "showtime")
@Data
@NoArgsConstructor
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Long showtimeId;
    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "hall_id")
    private Hall hall;
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    private Movie movie;
    private Timestamp time;
    @Column(name = "base_price")
    private Integer basePrice;
}
