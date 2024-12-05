package com.tenten.outsourcing.review.dto;

import com.tenten.outsourcing.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {

  private String userName;

  private Integer rating;

  private String content;

  public static ReviewResponseDto toDto(Review review) {
    return new ReviewResponseDto(
        review.getUser().getName(),
        review.getRating(),
        review.getContent()
    );
  }
}
