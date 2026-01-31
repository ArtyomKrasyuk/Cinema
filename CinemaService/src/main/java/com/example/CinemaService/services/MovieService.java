package com.example.CinemaService.services;

import com.example.CinemaService.dto.MovieRequestDTO;
import com.example.CinemaService.dto.MovieResponseDTO;
import com.example.CinemaService.mappers.MovieMapper;
import com.example.CinemaService.models.Movie;
import com.example.CinemaService.repos.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public long save(MovieRequestDTO dto){
        return movieRepository.save(movieMapper.toEntity(dto)).getMovieId();
    }

    public List<MovieResponseDTO> findAll(){
        return movieRepository.findAll().stream().map(movieMapper::toDto).toList();
    }

    public MovieResponseDTO findById(long movieId){
        Movie movie =  movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Фильм с id " + movieId + "не найден"));
        return movieMapper.toDto(movie);
    }

    public void update(MovieRequestDTO dto, long movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Фильм с id " + movieId + "не найден"));
        movieMapper.update(dto, movie);
        movieRepository.save(movie);
    }

    public void deleteById(long movieId){
        movieRepository.deleteById(movieId);
    }
}
