package com.example.CinemaService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;
    @Column(unique = true)
    private String title;

    @ManyToMany(mappedBy = "genres")
    @EqualsAndHashCode.Exclude
    Set<Movie> movies;
}
