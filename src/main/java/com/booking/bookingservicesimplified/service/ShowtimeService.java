package com.booking.bookingservicesimplified.service;

import com.booking.bookingservicesimplified.dto.ShowtimeRequestDetail;
import com.booking.bookingservicesimplified.dto.response.ShowtimeDetail;
import com.booking.bookingservicesimplified.entity.Movie;
import com.booking.bookingservicesimplified.entity.Showtime;
import com.booking.bookingservicesimplified.mapper.ShowtimeMapper;
import com.booking.bookingservicesimplified.repository.MovieRepository;
import com.booking.bookingservicesimplified.repository.ShowtimeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowtimeService {

  private final ShowtimeRepository showtimeRepository;
  private final MovieService movieService;
  private final MovieRepository movieRepository;

  public List<ShowtimeDetail> getAllShowtimes() {
    List<Showtime> showtimes = showtimeRepository.findAll();
    return showtimes.stream()
        .map(ShowtimeMapper::mapToShowtimeDetail)
        .toList();
  }

  public ShowtimeDetail getShowtimeById(Long id) {
    return showtimeRepository.findById(id)
        .map((ShowtimeMapper::mapToShowtimeDetail))
        .orElseThrow(() -> new RuntimeException("Showtime not found"));
  }

  public ShowtimeDetail addShowtime(ShowtimeRequestDetail requestDetail) {
    Movie movie = movieRepository.findById(requestDetail.getMovieId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));

    Showtime showtime = Showtime.builder()
        .movie(movie)
        .startTime(requestDetail.getStartTime())
        .availableSeats(requestDetail.getAvailableSeats())
        .build();
    Showtime savedShowtime = showtimeRepository.save(showtime);
    return ShowtimeMapper.mapToShowtimeDetail(savedShowtime);
  }

  public void deleteShowtime(Long id) {
    showtimeRepository.deleteById(id);
  }
}
