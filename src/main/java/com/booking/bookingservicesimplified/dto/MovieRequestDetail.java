package com.booking.bookingservicesimplified.dto;

import com.booking.bookingservicesimplified.dto.response.ShowtimeDetail;
import java.util.List;
import lombok.Data;

@Data
public class MovieRequestDetail {

  private long id;
  private String title;
  private String director;
  private String genre;
  private List<ShowtimeDetail> showtimes;
  private String signature;
}
