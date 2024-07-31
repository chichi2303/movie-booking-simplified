package com.booking.bookingservicesimplified.mapper;

import com.booking.bookingservicesimplified.dto.response.ShowtimeDetail;
import com.booking.bookingservicesimplified.entity.Movie;
import com.booking.bookingservicesimplified.entity.Showtime;

public class ShowtimeMapper {

  public static ShowtimeDetail mapToShowtimeDetail(Showtime showtime) {
    return ShowtimeDetail.builder()
        .id(showtime.getId())
        .startTime(showtime.getStartTime())
        .availableSeats(showtime.getAvailableSeats())
        .movieId(showtime.getMovie().getId())
        .build();
  }

  public static Showtime mapToShowtime(ShowtimeDetail showtimeDetail, Movie movie) {
    return Showtime.builder()
        .id(showtimeDetail.getId())
        .startTime(showtimeDetail.getStartTime())
        .availableSeats(showtimeDetail.getAvailableSeats())
        .movie(movie)
        .build();
  }

}
