package com.tenten.outsourcing.review.service;

import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.exception.NoAuthorizedException;
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tenten.outsourcing.exception.ErrorCode.NO_DELIVERY_ALREADY;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final OrderService orderService;

    public ReviewResponseDto save(@Valid ReviewRequestDto reviewRequestDto, Long userId) {

        User user = userService.findByIdOrElseThrow(userId);
        Order order = orderService.findOrderByIdOrElseThrow(reviewRequestDto.getOrderId());
        Store store = storeService.findById(order.getStore().getId());
        if (!DeliveryStatus.DELIVERED.equals(order.getStatus())) {
            throw new NoAuthorizedException(NO_DELIVERY_ALREADY);
        }
        Review review = new Review(user, store, order, reviewRequestDto.getRating(), reviewRequestDto.getContent());
        reviewRepository.save(review);
        return new ReviewResponseDto(
                review.getUser().getName(),
                review.getRating(),
                review.getContent()
        );
    }

    public List<ReviewResponseDto> getAll(Long userId, Long orderId, Integer lowRating, Integer highRating, Boolean sortRating, Pageable pageable) {

        Order order = orderService.findOrderByIdOrElseThrow(orderId);

        String query = "select r "
                + "from Review r "
                + "where r.user.id != " + userId + " "
            + "and r.store.id = " + order.getStore().getId() + " ";
        query += " and r.rating between " + lowRating + " and " + highRating + " ";

//        if (lowRating != null && highRating != null) {
//            query += " and r.rating between " + lowRating + " and " + highRating + " ";
//        } else if (lowRating != null) {
//            query += " and r.rating >= " + lowRating + " ";
//        } else if (highRating != null) {
//            query += " and r.rating <= " + highRating + " ";
//        }
        if (sortRating) {
            query += " order by r.rating desc, r.createdAt desc ";
        } else {
            query += " order by r.createdAt desc ";
        }

        List<Review> reviewList = entityManager.createQuery(query, Review.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return reviewList.stream()
                .map(ReviewResponseDto::toDto)
                .toList();
    }
}