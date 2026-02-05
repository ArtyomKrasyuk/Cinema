package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.CinemaResponseDTO;
import com.example.CinemaService.dto.ShowtimeRequestDTO;
import com.example.CinemaService.dto.ShowtimeResponseDTO;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.models.Movie;
import com.example.CinemaService.models.Showtime;
import com.example.CinemaService.repos.HallRepository;
import com.example.CinemaService.repos.MovieRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public abstract class ShowtimeMapper {
    @Autowired
    protected HallRepository hallRepository;
    @Autowired
    protected MovieRepository movieRepository;
    @Autowired
    protected CinemaMapper cinemaMapper;

    @Mapping(target = "cinema", source = "hall", qualifiedByName = "setCinema")
    @Mapping(target = "hallId", expression = "java(showtime.getHall().getHallId())")
    @Mapping(target = "movieTitle", expression = "java(showtime.getMovie().getTitle())")
    @Mapping(target = "time", expression = "java(showtime.getTime().toString())")
    public abstract ShowtimeResponseDTO toDto(Showtime showtime);

    @Mapping(target = "showtimeId", ignore = true)
    @Mapping(target = "hall", source = "hallId", qualifiedByName = "setHall")
    @Mapping(target = "movie", source = "movieTitle", qualifiedByName = "setMovie")
    @Mapping(target = "time", source = "time", qualifiedByName = "setTime")
    public abstract Showtime toEntity(ShowtimeRequestDTO dto);

    @Mapping(target = "showtimeId", ignore = true)
    @Mapping(target = "hall", source = "hallId", qualifiedByName = "setHall")
    @Mapping(target = "movie", source = "movieTitle", qualifiedByName = "setMovie")
    @Mapping(target = "time", source = "time", qualifiedByName = "setTime")
    public abstract void update(ShowtimeRequestDTO dto, @MappingTarget Showtime showtime);

    @Named("setCinema")
    public CinemaResponseDTO setCinema(Hall hall){
        return cinemaMapper.toDto(hall.getCinema());
    }

    @Named("setHall")
    public Hall setHall(long hallId){
        return hallRepository.findById(hallId).orElseThrow(() -> new RuntimeException("Зал с id " + hallId + " не найден"));
    }

    @Named("setMovie")
    public Movie setMovie(String movieTitle){
        return movieRepository.findByTitle(movieTitle).orElseThrow(
                () -> new RuntimeException("Фильм с названием " + movieTitle + " не найден")
        );
    }

    @Named("setTime")
    public Timestamp setTime(String time){
        return Timestamp.valueOf(time);
    }
}
