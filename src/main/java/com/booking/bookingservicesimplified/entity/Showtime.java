package com.booking.bookingservicesimplified.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Showtime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private LocalDateTime startTime;
  private int availableSeats;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_id", insertable = false, updatable = false)
  private Movie movie;

}
