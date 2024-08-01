package com.booking.bookingservicesimplified.service;


import com.booking.bookingservicesimplified.dto.MovieRequestDetail;
import com.booking.bookingservicesimplified.dto.response.MovieDetail;
import com.booking.bookingservicesimplified.dto.response.ShowtimeDetail;
import com.booking.bookingservicesimplified.entity.Movie;
import com.booking.bookingservicesimplified.entity.Showtime;
import com.booking.bookingservicesimplified.mapper.MovieMapper;
import com.booking.bookingservicesimplified.mapper.ShowtimeMapper;
import com.booking.bookingservicesimplified.repository.MovieRepository;
import com.booking.bookingservicesimplified.repository.ShowtimeRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;
  private final ShowtimeRepository showtimeRepository;

  public List<MovieDetail> getAllMovies() {
    List<Movie> movies = movieRepository.findAll();
    return movies.stream()
        .map(MovieMapper::mapToMovieDetail)
        .toList();
  }

  public Optional<MovieDetail> getMovieById(Long id) {
    return movieRepository.findById(id)
        .map(MovieMapper::mapToMovieDetail);
  }

  @Transactional
  public MovieDetail addMovie(MovieRequestDetail movieRequestDetail) {
    if (movieRepository.existbyid(movieRequestDetail.getId())) {
      throw new RuntimeException("Movie with id " + movieRequestDetail.getId() + " already exists");
    }
    Movie movie = MovieMapper.mapToMovie(movieRequestDetail);

    // Save the movie first to get its ID
    Movie savedMovie = movieRepository.save(movie);

    // Update showtimes with correct movie reference
    for (ShowtimeDetail showtime : movieRequestDetail.getShowtimes()) {
      showtime.setMovieId(savedMovie.getId());
    }
    showtimeRepository.saveAll(ShowtimeMapper.mapToShowtimes(movieRequestDetail.getShowtimes()));
    return MovieMapper.mapToMovieDetail(savedMovie);
  }

  @Transactional
  public List<MovieDetail> addMovies(List<MovieRequestDetail> movieRequestDetails) {
    List<Movie> movies = movieRequestDetails.stream()
        .map(MovieMapper::mapToMovie)
        .toList();

    movies.forEach(movie -> {
      if (movieRepository.existbyid(movie.getId())) {
        throw new RuntimeException("One or more movies already exists");
      }
      movie.getShowtimes().forEach(showtime -> showtime.setMovie(movie));
    });
    // Save movies first to get their IDs
    List<Movie> savedMovies = movieRepository.saveAll(movies);

    //update showtimes with movie reference
    savedMovies.forEach(movie -> {
      for (Showtime showtime : movie.getShowtimes()) {
        showtime.setMovie(movie);
      }
      showtimeRepository.saveAll(movie.getShowtimes());
    });

    return savedMovies.stream()
        .map(MovieMapper::mapToMovieDetail)
        .toList();
  }

  public List<MovieDetail> updateMovies(List<MovieRequestDetail> movieRequestDetails) {
    List<Movie> updatedMovies = new ArrayList<>();
    for (MovieRequestDetail movieRequestDetail : movieRequestDetails) {
      Movie existingMovie = movieRepository.findById(movieRequestDetail.getId())
          .orElseThrow(() -> new RuntimeException("Movie not found"));
      existingMovie.setTitle(movieRequestDetail.getTitle());
      existingMovie.setGenre(movieRequestDetail.getGenre());
      existingMovie.setDirector(movieRequestDetail.getDirector());

      existingMovie.getShowtimes().clear();
      existingMovie.getShowtimes().addAll(
          movieRequestDetail.getShowtimes().stream()
              .map(showtimeDetail -> {
                Showtime showtime = MovieMapper.toShowtime(showtimeDetail);
                showtime.setMovie(existingMovie);
                return showtime;
              })
              .toList()
      );
      updatedMovies.add(existingMovie);
    }
    List<Movie> savedMovies = movieRepository.saveAll(updatedMovies);
    return savedMovies.stream()
        .map(MovieMapper::mapToMovieDetail)
        .toList();
  }

  public void deleteMovie(long id) {
    if (showtimeRepository.existsByMovieId(id)) {
      throw new RuntimeException("Cannot delete movie with active showtimes");
    }
    movieRepository.deleteById(id);
  }
}
