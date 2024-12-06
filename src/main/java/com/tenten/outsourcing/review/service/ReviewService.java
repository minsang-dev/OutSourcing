package com.tenten.outsourcing.review.service;

import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.exception.DuplicatedException;
import com.tenten.outsourcing.exception.NoAuthorizedException;
import com.tenten.outsourcing.exception.NotFoundException;
import com.tenten.outsourcing.order.entity.Order;
import com.tenten.outsourcing.order.service.OrderService;
import com.tenten.outsourcing.review.dto.ReviewCommentRequestDto;
import com.tenten.outsourcing.review.dto.ReviewCommentResponsetDto;
import com.tenten.outsourcing.review.dto.ReviewRequestDto;
import com.tenten.outsourcing.review.dto.ReviewResponseDto;
import com.tenten.outsourcing.review.entity.Review;
import com.tenten.outsourcing.review.repository.ReviewRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.service.StoreService;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tenten.outsourcing.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final OrderService orderService;

    public ReviewResponseDto save(ReviewRequestDto reviewRequestDto, Long userId) {

        User user = userService.findByIdOrElseThrow(userId);
        Order order = orderService.findOrderByIdOrElseThrow(reviewRequestDto.getOrderId());
        Store store = storeService.findById(order.getStore().getId());

        // 리뷰가 이미 달렸으면 예외
        if (reviewRepository.existsByOrderId(order.getId())){
            throw new DuplicatedException(REVIEW_EXIST);
        }
        // 가게 주인은 리뷰를 달 수 없습니다.
        if (isOwner(user.getId(), store.getUser().getId())) {
            throw new NoAuthorizedException(NO_REVIEW_FOR_OWNER);
        }
        // 배달이 완료되지 않으면 리뷰를 달 수 없습니다.
        if (!DeliveryStatus.DELIVERED.equals(order.getStatus())) {
            throw new NoAuthorizedException(NO_DELIVERY_ALREADY);
        }

        Review review = new Review(user, store, order, reviewRequestDto.getRating(), reviewRequestDto.getContent());
        reviewRepository.save(review);
        return ReviewResponseDto.toDto(review, null);
    }

    public List<ReviewResponseDto> getAll(Long userId, Long orderId, Integer lowRating, Integer highRating, Boolean sortRating, Pageable pageable) {

        Order order = orderService.findOrderByIdOrElseThrow(orderId);
        Long storeId = order.getStore().getId();

        String query = "select r "
                + "from Review r "
                + "where r.user.id != :userId "
                + "and r.store.id = :storeId "
                + "and r.parents is null ";
        // 주어진 별점 사이의 결과를 조회
        query += "and r.rating between :lowRating and :highRating ";
        // 별점순으로 정렬할 것인지 결정 true 별점순 조회, false 기본조회
        if (sortRating) {
            query += " order by r.rating desc, r.createdAt desc ";
        } else {
            query += " order by r.createdAt desc ";
        }

        // 부모 리뷰 먼저 조회(대댓글이 없는 리뷰들)
        List<Review> parentReviews = entityManager.createQuery(
                        query,
                        Review.class)
                .setParameter("userId", userId)
                .setParameter("storeId", storeId)
                .setParameter("lowRating", lowRating)
                .setParameter("highRating", highRating)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // 각 부모 리뷰에 대해 대댓글을 조회하고, DTO로 즉시변환
        return parentReviews.stream().map(parentReview -> {
            // 부모 리뷰에 연결된 대댓글 조회 (한개만 있음)
            Review commentReview = entityManager.createQuery(
                            "SELECT r FROM Review r WHERE r.parents =: parentId", Review.class)
                    .setParameter("parentId", parentReview.getId())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            // DTO로 변환
            return ReviewResponseDto.toDto(parentReview, commentReview);
        }).collect(Collectors.toList());
    }

    public ReviewResponseDto saveComment(ReviewCommentRequestDto reviewCommentRequestDto, Long ownerId) {
        User owner = userService.findByIdOrElseThrow(ownerId);
        Review review = findByIdOrElseThrow(reviewCommentRequestDto.getReviewId());

        // 이미 답글 (parents)이 있다면 예외
        if (reviewRepository.existsByParents(reviewCommentRequestDto.getReviewId())) {
            throw new NoAuthorizedException(REVIEW_COMMENT_EXIST);
        }
        Store store = storeService.findById(review.getStore().getId());
        Order order = orderService.findOrderByIdOrElseThrow(review.getOrder().getId());
        
        // 가게의 주인이 아니라면 예외
        if (!isOwner(owner.getId(), store.getUser().getId())) {
            throw new NoAuthorizedException(NO_REVIEW_COMMENT_FOR_OWNER);
        }
        Review reviewComment = new Review(owner, store, order, reviewCommentRequestDto.getComment(), review.getId());
        reviewRepository.save(reviewComment);
        return ReviewResponseDto.toDto(reviewComment, null);
    }

    private Review findByIdOrElseThrow(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_REVIEW));
    }

    private boolean isOwner(Long userId, Long ownerId) {
        if (!userId.equals(ownerId)) {
            return false;
        }
        return true;
    }
}