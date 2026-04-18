package com.example.CinemaService.repos;

import com.example.CinemaService.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);

    @Query("SELECT m.title FROM Movie m WHERE LOWER(m.title) LIKE LOWER(concat('%', :title, '%'))")
    List<String> findTitlesBySubstring(@Param("title") String title);
}
