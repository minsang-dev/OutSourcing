package com.tenten.outsourcing.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    private Long orderId;

    @NotNull(message = "별점은 필수값입니다.")
    @Min(value = 1, message = "별점의 최솟값은 1점입니다.")
    @Max(value = 5, message = "별점의 최대값은 5점입니다.")
    private Integer rating;

    private String content;

}
