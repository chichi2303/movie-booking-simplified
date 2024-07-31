package com.booking.bookingservicesimplified.repository;

import com.booking.bookingservicesimplified.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRespository extends JpaRepository<Movie, Long> {

  @Query(value = "select Count(*)>0 from movie m  where m.id = :movieId", nativeQuery = true)
  boolean existbyid(@Param("movieId") Long movieId);

}
