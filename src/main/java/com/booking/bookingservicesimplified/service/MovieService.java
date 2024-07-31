package com.booking.bookingservicesimplified.service;


import com.booking.bookingservicesimplified.dto.MovieRequestDetail;
import com.booking.bookingservicesimplified.dto.response.MovieDetail;
import com.booking.bookingservicesimplified.entity.Movie;
import com.booking.bookingservicesimplified.entity.Showtime;
import com.booking.bookingservicesimplified.mapper.MovieMapper;
import com.booking.bookingservicesimplified.repository.MovieRespository;
import com.booking.bookingservicesimplified.repository.ShowtimeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRespository movieRespository;
  private final ShowtimeRepository showtimeRepository;

  public List<MovieDetail> getAllMovies() {
    List<Movie> movies = movieRespository.findAll();
    return movies.stream()
        .map(MovieMapper::mapToMovieDetail)
        .toList();
  }

  public Optional<MovieDetail> getMovieById(Long id) {
    return movieRespository.findById(id)
        .map(MovieMapper::mapToMovieDetail);
  }

  public MovieDetail addMovie(MovieRequestDetail movieRequestDetail) {
    if (movieRespository.existbyid(movieRequestDetail.getId())) {
      throw new RuntimeException("Movie with id " + movieRequestDetail.getId() + " already exists");
    }
    Movie movie = MovieMapper.mapToMovie(movieRequestDetail);
    movie.getShowtimes().forEach(showtime -> showtime.setMovie(movie));
    Movie savedMovie = movieRespository.save(movie);
    return MovieMapper.mapToMovieDetail(savedMovie);
  }

  public List<MovieDetail> addMovies(List<MovieRequestDetail> movieRequestDetails) {
    List<Movie> movies = movieRequestDetails.stream()
        .map(MovieMapper::mapToMovie)
        .toList();

    movies.forEach(movie -> {
      if (movieRespository.existbyid(movie.getId())) {
        throw new RuntimeException("One or more movies already exists");
      }
      movie.getShowtimes().forEach(showtime -> showtime.setMovie(movie));
    });

    List<Movie> savedMovies = movieRespository.saveAll(movies);
    return savedMovies.stream()
        .map(MovieMapper::mapToMovieDetail)
        .toList();
  }

  public List<MovieDetail> updateMovies(List<MovieRequestDetail> movieRequestDetails) {
    List<Movie> updatedMovies = new ArrayList<>();
    for (MovieRequestDetail movieRequestDetail : movieRequestDetails) {
      Movie existingMovie = movieRespository.findById(movieRequestDetail.getId())
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
    List<Movie> savedMovies = movieRespository.saveAll(updatedMovies);
    return savedMovies.stream()
        .map(MovieMapper::mapToMovieDetail)
        .toList();
  }

  public void deleteMovie(long id) {
    if (showtimeRepository.existsByMovieId(id)) {
      throw new RuntimeException("Cannot delete movie with active showtimes");
    }
    movieRespository.deleteById(id);
  }
}

