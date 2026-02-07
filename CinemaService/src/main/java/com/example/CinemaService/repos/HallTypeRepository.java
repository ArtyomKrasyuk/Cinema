package com.example.CinemaService.repos;

import com.example.CinemaService.models.HallType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallTypeRepository extends JpaRepository<HallType, Long> {
    Optional<HallType> findByTitle(String title);
}
