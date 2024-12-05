package com.tenten.outsourcing.review.controller;

import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.review.dto.ReviewRequestDto;
import com.tenten.outsourcing.review.dto.ReviewResponseDto;
import com.tenten.outsourcing.review.service.ReviewService;

import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.user.dto.SessionDto;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public ResponseEntity<ReviewResponseDto> save(
      @Valid @RequestBody ReviewRequestDto reviewRequestDto,
      @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
  ){
    ReviewResponseDto reviewResponseDto = reviewService.save(reviewRequestDto, session.getId());
    return ResponseEntity.ok().body(reviewResponseDto);
  }

  @GetMapping("/orders/{orderId}")
  public ResponseEntity<List<ReviewResponseDto>> getAll(
      @PathVariable Long orderId,
      @RequestParam Integer lowRating,
      @RequestParam Integer highRating,
      @RequestParam Boolean sortRating,
      Pageable pageable,
      @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
  ){
    lowRating = lowRating != null ? lowRating : 0;
    highRating = highRating != null ? highRating : 5;
    sortRating = sortRating != null ? sortRating : false;
    List<ReviewResponseDto> list = reviewService.getAll(session.getId(), orderId, lowRating, highRating, sortRating, pageable);
   return ResponseEntity.ok().body(list);
  }
}
