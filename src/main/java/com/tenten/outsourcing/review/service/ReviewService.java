package com.tenten.outsourcing.review.service;

import com.tenten.outsourcing.order.dto.OrderResponseDto;
import com.tenten.outsourcing.order.entity.Order;
import com.tenten.outsourcing.order.service.OrderService;
import com.tenten.outsourcing.review.dto.ReviewRequestDto;
import com.tenten.outsourcing.review.dto.ReviewResponseDto;
import com.tenten.outsourcing.review.entity.Review;
import com.tenten.outsourcing.review.repository.ReviewRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.service.StoreService;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserService userService;
  private final StoreService storeService;
  private final OrderService orderService;

  public ReviewResponseDto save(@Valid ReviewRequestDto reviewRequestDto, Long userId) {

    User user = userService.findByIdOrElseThrow(userId);
//    OrderResponseDto orderResponseDto = orderService.findOrder(reviewRequestDto.getOrderId(), userId);
    Order order = null;
//    Store store = storeService
    Store store = null;
    Review review = new Review(user, store, order, reviewRequestDto.getRating(), reviewRequestDto.getContent());
    reviewRepository.save(review);
    return new ReviewResponseDto(
        review.getRating(),
        review.getContent()
    );
  }

}
