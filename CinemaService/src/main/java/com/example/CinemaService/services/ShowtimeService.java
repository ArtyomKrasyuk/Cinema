package com.example.CinemaService.services;

import com.example.CinemaService.dto.ShowtimeRequestDTO;
import com.example.CinemaService.dto.ShowtimeResponseDTO;
import com.example.CinemaService.mappers.ShowtimeMapper;
import com.example.CinemaService.models.Showtime;
import com.example.CinemaService.repos.ShowtimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeMapper showtimeMapper;

    public List<ShowtimeResponseDTO> findAll(){
        return showtimeRepository.findAllUpcomingShowtimes().stream().map(showtimeMapper::toDto).toList();
    }

    public void save(ShowtimeRequestDTO dto){
        Showtime showtime = showtimeMapper.toEntity(dto);
        if(isTimeInvalid(showtime, false)) throw new RuntimeException("Выбранный зал уже имеет сеанс в это время");
        showtimeRepository.save(showtime);
    }

    public void update(ShowtimeRequestDTO dto, long showtimeId){
        Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(
                () -> new RuntimeException("Не найден киносеанс с id " + showtimeId)
        );
        showtimeMapper.update(dto, showtime);
        if(isTimeInvalid(showtime, true)) throw new RuntimeException("Выбранный зал уже имеет сеанс в это время");
        showtimeRepository.save(showtime);
    }

    public void deleteById(long showtimeId){
        showtimeRepository.deleteById(showtimeId);
    }

    private boolean isTimeInvalid(Showtime showtime, boolean update){
        Timestamp endTime = Timestamp.from(
                showtime.getTime().toInstant().plus(showtime.getMovie().getDuration(), ChronoUnit.MINUTES)
        );
        if(update){
            for(Showtime elem: showtimeRepository.findConflicts(showtime.getTime(), endTime, showtime.getHall().getHallId())){
                if(!elem.getShowtimeId().equals(showtime.getShowtimeId())) return true;
            }
            return false;
        }
        else return !showtimeRepository.findConflicts(showtime.getTime(), endTime, showtime.getHall().getHallId()).isEmpty();
    }
}
