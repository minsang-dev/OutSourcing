package com.tenten.outsourcing.review.controller;

import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.review.dto.ReviewRequestDto;
import com.tenten.outsourcing.review.dto.ReviewResponseDto;
import com.tenten.outsourcing.review.service.ReviewService;
import com.tenten.outsourcing.user.dto.SessionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 생성
     * 해당 가게의 사장은 작성 불가능
     * @param reviewRequestDto
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<ReviewResponseDto> save(
            @Valid @RequestBody ReviewRequestDto reviewRequestDto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {
        ReviewResponseDto reviewResponseDto = reviewService.save(reviewRequestDto, session.getId());
        return ResponseEntity.ok().body(reviewResponseDto);
    }

    /**
     * 리뷰 조회
     *
     * @param orderId       주문 아이디
     * @param lowRating     조회시 최소별점
     * @param highRating    조회시 최대별점
     * @param sortRating    별점순 정렬여부
     * @param pageable
     * @param session
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<List<ReviewResponseDto>> getAll(
            @PathVariable Long orderId,
            @RequestParam(required = false) Integer lowRating,
            @RequestParam(required = false) Integer highRating,
            @RequestParam(required = false) Boolean sortRating,
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {
        lowRating = lowRating != null ? lowRating : 0;
        highRating = highRating != null ? highRating : 5;
        sortRating = sortRating != null ? sortRating : false;
        List<ReviewResponseDto> list = reviewService.getAll(session.getId(), orderId, lowRating, highRating, sortRating, pageable);
        return ResponseEntity.ok().body(list);
    }

    // 사장 답글
//    @PostMapping("/comments")
//    public ResponseEntity<ReviewResponseDto> comment(
//        @Valid @RequestBody ReviewRequestDto reviewRequestDto,
//        @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
//    ){
//        ReviewResponseDto reviewResponseDto = reviewRequestDto.saveComment(reviewRequestDto, session.getId());
//        return ResponseEntity.ok().body(reviewResponseDto);
//    }
}
