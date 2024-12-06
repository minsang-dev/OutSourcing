package com.tenten.outsourcing.order.repository;


import com.tenten.outsourcing.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 유저의 주문을 생성일자 기준 내림차순 정렬
     *
     * @param userId 유저 식별자
     * @return 주문 목록
     */
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Order> findAllOrdersByUserId(@Param("userId") Long userId, Pageable pageable);
}
