package com.tenten.outsourcing.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewCommentResponsetDto {

    private Long userId;

    private String comment;

    private LocalDateTime updatedAt;

}
