package com.tenten.outsourcing.review.dto;

import com.tenten.outsourcing.review.entity.Review;

import java.util.List;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {

    // review.getId
    private Long reviewId;

    // review.getUser().getId
    private Long userId;

    // review.getStore().getId
    private Long storeId;

    // review.getOrder().getId
    private Long orderId;

    // review.getUser().getUserName
    private String userName;

    // review.getRating()
    private Integer rating;

    // review.getContent
    private String content;

    private ReviewCommentResponsetDto comments;

    // review.getCreateAt
    private LocalDateTime createAt;

    public static ReviewResponseDto toDto(Review review, Review commentReview) {
        ReviewCommentResponsetDto comment = null;

        // 대댓글이 있다면 Dto로 변환
        if (commentReview != null) {
            comment = new ReviewCommentResponsetDto(
                    commentReview.getUser().getId(),
                    commentReview.getContent(),
                    commentReview.getUpdatedAt()
            );
        }

        return new ReviewResponseDto(
                review.getId(),
                review.getUser().getId(),
                review.getStore().getId(),
                review.getOrder().getId(),
                review.getUser().getName(),
                review.getRating(),
                review.getContent(),
                comment,
                review.getCreatedAt()
        );
    }
}
