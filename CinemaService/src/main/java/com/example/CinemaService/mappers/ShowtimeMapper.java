package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.*;
import com.example.CinemaService.models.Hall;
import com.example.CinemaService.models.Movie;
import com.example.CinemaService.models.Seat;
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
    @Autowired
    protected MovieMapper movieMapper;
    @Autowired
    protected HallMapper hallMapper;

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

    @Mapping(target = "movie", source = "movie", qualifiedByName = "returnMovieDto")
    @Mapping(target = "time", expression = "java(showtime.getTime().toString())")
    public abstract ShowtimeWithMovieResponseDTO toDtoWithMovie(Showtime showtime);

    @Mapping(target = "cinema", source = "hall", qualifiedByName = "setCinema")
    @Mapping(target = "hallId", expression = "java(showtime.getHall().getHallId())")
    @Mapping(target = "movieTitle", expression = "java(showtime.getMovie().getTitle())")
    @Mapping(target = "time", expression = "java(showtime.getTime().toString())")
    @Mapping(target = "minPrice", source = ".", qualifiedByName = "setMinPrice")
    public abstract ShowtimeWithMinPriceResponseDTO toDtoWithMinPrice(Showtime showtime);

    @Mapping(target = "hall", source = "hall", qualifiedByName = "setHallWithFactor")
    @Mapping(target = "movieTitle", expression = "java(showtime.getMovie().getTitle())")
    @Mapping(target = "cinemaTitle", expression = "java(showtime.getHall().getCinema().getTitle())")
    @Mapping(target = "cinemaAddress", expression = "java(showtime.getHall().getCinema().getAddress())")
    @Mapping(target = "time", expression = "java(showtime.getTime().toString())")
    public abstract ShowtimeWithHallResponseDTO toDtoWithHall(Showtime showtime);

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

    @Named("returnMovieDto")
    public MovieResponseDTO returnMovieDto(Movie movie){
        return movieMapper.toDto(movie);
    }

    @Named("setTime")
    public Timestamp setTime(String time){
        return Timestamp.valueOf(time);
    }

    @Named("setMinPrice")
    public int setMinPrice(Showtime showtime){
        double minPrice = Double.MAX_VALUE;
        double price = showtime.getBasePrice() * showtime.getHall().getHallType().getFactor();
        for(Seat seat: showtime.getHall().getSeats()){
            if(price * seat.getSeatType().getFactor() < minPrice) minPrice = price * seat.getSeatType().getFactor();
        }
        return (int)Math.floor(minPrice);
    }

    @Named("setHallWithFactor")
    public HallWithFactorResponseDTO setHallWithFactor(Hall hall){
        return hallMapper.toDtoWithFactor(hall);
    }
}
