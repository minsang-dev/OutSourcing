package com.tenten.outsourcing.repository;


import com.tenten.outsourcing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
