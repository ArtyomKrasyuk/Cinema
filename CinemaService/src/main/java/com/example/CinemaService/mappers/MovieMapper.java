package com.example.CinemaService.mappers;

import com.example.CinemaService.dto.MovieRequestDTO;
import com.example.CinemaService.dto.MovieResponseDTO;
import com.example.CinemaService.models.Genre;
import com.example.CinemaService.models.Movie;
import com.example.CinemaService.repos.GenreRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public abstract class MovieMapper {
    @Autowired
    protected GenreRepository genreRepository;

    @Mapping(target = "movieId", ignore = true)
    @Mapping(target = "showtimes", ignore = true)
    @Mapping(target = "genres", source = "genres", qualifiedByName = "setGenres")
    public abstract Movie toEntity(MovieRequestDTO dto);

    @Mapping(target = "genres", source = "genres", qualifiedByName = "setGenresToDto")
    public abstract MovieResponseDTO toDto(Movie movie);

    @Mapping(target = "movieId", ignore = true)
    @Mapping(target = "showtimes", ignore = true)
    @Mapping(target = "genres", source = "genres", qualifiedByName = "setGenres")
    public abstract void update(MovieRequestDTO dto, @MappingTarget Movie movie);

    @Named("setGenres")
    public Set<Genre> setGenres(List<String> genres){
        return genres.stream().map(str -> genreRepository.findByTitle(str)
                .orElseThrow(() -> new RuntimeException("Жанр с названием " + str + "не найден")))
                .collect(Collectors.toSet());
    }

    @Named("setGenresToDto")
    public List<String> setGenresToDto(Set<Genre> genres){
        return genres.stream().map(Genre::getTitle).toList();
    }
}
