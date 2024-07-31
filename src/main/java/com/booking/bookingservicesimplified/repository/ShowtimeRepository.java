package com.booking.bookingservicesimplified.repository;

import com.booking.bookingservicesimplified.entity.Showtime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

  List<Showtime> findByMovieId(Long id);


  @Query(value = "SELECT COUNT(*) > 0 FROM showtime WHERE movie_id = :movieId", nativeQuery = true)
  boolean existsByMovieId(@Param("movieId") Long movieId);


}
