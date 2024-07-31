package com.booking.bookingservicesimplified.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class ShowtimeDetail {

  private long id;
  private LocalDateTime startTime;
  private int availableSeats;
  private long movieId;
}
