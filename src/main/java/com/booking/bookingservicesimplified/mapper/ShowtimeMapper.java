package com.booking.bookingservicesimplified.mapper;

import com.booking.bookingservicesimplified.dto.response.ShowtimeDetail;
import com.booking.bookingservicesimplified.entity.Movie;
import com.booking.bookingservicesimplified.entity.Showtime;
import java.util.List;
import lombok.val;

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

  public static List<Showtime> mapToShowtimes(List<ShowtimeDetail> showtimes) {
      return showtimes.stream().map(s -> {
      val showTime = new Showtime();
      showTime.setStartTime(s.getStartTime());
      showTime.setMovieId(s.getMovieId());
      showTime.setAvailableSeats(s.getAvailableSeats());
      return showTime;
    }).toList();
  }
}
