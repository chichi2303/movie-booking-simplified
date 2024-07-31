package com.booking.bookingservicesimplified.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ShowtimeRequestDetail {

  private long id;
  private LocalDateTime startTime;
  private int availableSeats;
  private long movieId;
  private String signature;
}
