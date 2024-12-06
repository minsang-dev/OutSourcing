package com.tenten.outsourcing.review.dto;

import com.tenten.outsourcing.review.entity.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {

  private Long reviewId;

  private Long userId;

  private Long storeId;

  private Long orderId;

  private String userName;

  private Integer rating;

  private String content;

  private LocalDateTime createAt;

  public static ReviewResponseDto toDto(Review review) {
    return new ReviewResponseDto(
        review.getId(),
        review.getUser().getId(),
        review.getStore().getId(),
        review.getOrder().getId(),
        review.getUser().getName(),
        review.getRating(),
        review.getContent(),
        review.getCreatedAt()
    );
  }
}
