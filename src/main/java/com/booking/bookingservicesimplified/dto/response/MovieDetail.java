package com.booking.bookingservicesimplified.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class MovieDetail {

  private Long id;
  private String title;
  private String genre;
  private String director;

}
