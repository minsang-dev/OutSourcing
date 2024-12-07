package com.tenten.outsourcing.review.repository;

import com.tenten.outsourcing.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByParents(Long parents);

    boolean existsByParents(Long reviewId);

    boolean existsByOrderId(Long id);
}
