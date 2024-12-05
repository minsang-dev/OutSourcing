package com.tenten.outsourcing.review.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;
  private final UserService userService;

  @PostMapping
  public ResponseEntity<ReviewResponseDto> save(
      @Valid @RequestBody ReviewRequestDto reviewRequestDto,
      HttpServletRequest request
  ){
    SessionDto session = userService.getSession(request);
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
      HttpServletRequest request
  ){
    Long userId = userService.getSession(request).getId();
    lowRating = lowRating != null ? lowRating : 0;
    highRating = highRating != null ? highRating : 5;
    sortRating = sortRating != null ? sortRating : false;
    List<ReviewResponseDto> list = reviewService.getAll(userId, orderId, lowRating, highRating, sortRating, pageable);
   return ResponseEntity.ok().body(list);
  }
}
