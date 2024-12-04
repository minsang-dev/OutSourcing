package com.tenten.outsourcing.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

  private Long orderId;

  @NotNull(message = "별점은 필수값입니다.")
  private Integer rating;

  private String content;

}
