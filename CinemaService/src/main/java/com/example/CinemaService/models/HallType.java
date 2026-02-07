package com.example.CinemaService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "hall_type")
@Data
@NoArgsConstructor
public class HallType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_type_id")
    private Long hallTypeId;
    @Column(unique = true)
    private String title;
    private Double factor;

    @OneToMany(mappedBy = "hallType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Hall> halls;
}
