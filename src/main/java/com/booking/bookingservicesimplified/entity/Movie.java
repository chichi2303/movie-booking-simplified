package com.booking.bookingservicesimplified.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String title;
  private String director;
  private String genre;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Showtime> showtimes;

}
