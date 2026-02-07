package com.example.CinemaService.repos;

import com.example.CinemaService.models.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    @Query("SELECT s FROM Showtime s WHERE s.time > CURRENT_TIMESTAMP")
    List<Showtime> findAllUpcomingShowtimes();

    @Query(value = """
        SELECT s.*
        FROM showtime s
        JOIN movie m ON m.movie_id = s.movie_id
        WHERE s.hall_id = :hallId
          AND tsrange(s.time,
                      s.time + m.duration * interval '1 minute')
              && tsrange(:startTime, :endTime)
    """, nativeQuery = true)
    List<Showtime> findConflicts(
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime,
            @Param("hallId") Long hallId
    );

    @Query("SELECT s FROM Showtime s WHERE s.time > CURRENT_TIMESTAMP AND s.movie.movieId = :movie")
    List<Showtime> findAllUpcomingShowtimesByMovie(long movieId);
}
