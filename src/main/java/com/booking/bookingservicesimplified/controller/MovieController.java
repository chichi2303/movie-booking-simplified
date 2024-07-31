package com.booking.bookingservicesimplified.controller;

import com.booking.bookingservicesimplified.dto.MovieRequestDetail;
import com.booking.bookingservicesimplified.dto.response.MovieDetail;
import com.booking.bookingservicesimplified.entity.Movie;
import com.booking.bookingservicesimplified.service.MovieService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping
  public ResponseEntity<List<MovieDetail>> getAllMovies() {
    List<MovieDetail> allMovies = movieService.getAllMovies();
    return new ResponseEntity<>(allMovies, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieDetail> getMovieById(@PathVariable long id) {
    Optional<MovieDetail> movie = movieService.getMovieById(id);
    return movie.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<MovieDetail> createMovie(
      @RequestBody MovieRequestDetail movieRequestDetail) {
    MovieDetail addMovie = movieService.addMovie(movieRequestDetail);
    return new ResponseEntity<>(addMovie, HttpStatus.CREATED);
  }

  @PostMapping("/batch")
  public ResponseEntity<List<MovieDetail>> createMovies(
      @RequestBody List<MovieRequestDetail> movieRequestDetails) {
    List<MovieDetail> addMovies = movieService.addMovies(movieRequestDetails);
    return new ResponseEntity<>(addMovies, HttpStatus.CREATED);
  }

  @PutMapping("/batch")
  public ResponseEntity<List<MovieDetail>> updateMovies(
      @RequestBody List<MovieRequestDetail> movieRequestDetails) {
    List<MovieDetail> updateMovies = movieService.updateMovies(movieRequestDetails);
    return new ResponseEntity<>(updateMovies, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMovieById(@PathVariable long id) {
    movieService.deleteMovie(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
