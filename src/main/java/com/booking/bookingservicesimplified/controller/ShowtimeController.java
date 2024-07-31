package com.booking.bookingservicesimplified.controller;

import com.booking.bookingservicesimplified.dto.ShowtimeRequestDetail;
import com.booking.bookingservicesimplified.dto.response.ShowtimeDetail;
import com.booking.bookingservicesimplified.service.ShowtimeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

  private final ShowtimeService showtimeService;

  @GetMapping
  public ResponseEntity<List<ShowtimeDetail>> getAllShowtimes() {
    List<ShowtimeDetail> showtimes = showtimeService.getAllShowtimes();
    return new ResponseEntity<>(showtimes, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShowtimeDetail> getShowtimeById(@PathVariable Long id) {
    ShowtimeDetail showtime = showtimeService.getShowtimeById(id);
    return new ResponseEntity<>(showtime, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ShowtimeDetail> addShowtime(@RequestBody ShowtimeRequestDetail request) {
    ShowtimeDetail addedShowtime = showtimeService.addShowtime(request);
    return new ResponseEntity<>(addedShowtime, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
    showtimeService.deleteShowtime(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
