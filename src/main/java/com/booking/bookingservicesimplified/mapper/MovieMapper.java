package com.booking.bookingservicesimplified.mapper;

import com.booking.bookingservicesimplified.dto.MovieRequestDetail;
import com.booking.bookingservicesimplified.dto.response.MovieDetail;
import com.booking.bookingservicesimplified.dto.response.ShowtimeDetail;
import com.booking.bookingservicesimplified.entity.Movie;
import com.booking.bookingservicesimplified.entity.Showtime;

public class MovieMapper {

  //Map from Db to DTO
  public static MovieDetail mapToMovieDetail(Movie movie) {
    return MovieDetail.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .genre(movie.getGenre())
        .director(movie.getDirector())
        .build();
  }

  //Map from DTO to DB
  public static Movie mapToMovie(MovieRequestDetail movieRequestDetail) {
    Movie movie = Movie.builder()
        .id(movieRequestDetail.getId())
        .title(movieRequestDetail.getTitle())
        .genre(movieRequestDetail.getGenre())
        .director(movieRequestDetail.getDirector())
        .build();
    if (movieRequestDetail.getShowtimes() != null) {
      movie.setShowtimes(movieRequestDetail.getShowtimes().stream()
          .map(MovieMapper::toShowtime)
          .toList());
    }
    return movie;
  }

  public static Showtime toShowtime(ShowtimeDetail showtimeDetail) {
    return Showtime.builder()
        .id(showtimeDetail.getId())
        .startTime(showtimeDetail.getStartTime())
        .availableSeats(showtimeDetail.getAvailableSeats())
        .build();
  }
}
