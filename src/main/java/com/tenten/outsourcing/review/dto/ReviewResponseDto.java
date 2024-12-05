package com.tenten.outsourcing.review.dto;

import com.tenten.outsourcing.review.entity.Review;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewResponseDto {

  private Integer rating;

  private String content;

  public static ReviewResponseDto toDto(Review review) {
    return new ReviewResponseDto(
        review.getRating(),
        review.getContent()
    );
  }
}
