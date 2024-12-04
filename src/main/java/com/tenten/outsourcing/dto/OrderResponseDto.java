package com.tenten.outsourcing.dto;

import com.tenten.outsourcing.entity.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderResponseDto {

    private final Long menuId;

    private final int totalPrice;

    private final String request;

    private final String type;

    private final LocalDateTime createdAt;

    public OrderResponseDto(Order order) {
        this.menuId = order.getMenu().getId();
        this.totalPrice = order.getTotalPrice();
        this.request = order.getRequestMessage();
        this.type = order.getType().getText();
        this.createdAt = order.getCreatedAt();
    }

}
