package com.tenten.outsourcing.order.dto;

import com.tenten.outsourcing.order.entity.Order;
import com.tenten.outsourcing.order.entity.OrderMenu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderResponseDto {

    private final Long orderId;

    private final Long storeId;

    private final List<Long> orderMenuIds;

    private final int totalPrice;

    private final String request;

    private final String type;

    private final String status;

    private final LocalDateTime createdAt;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.storeId = order.getStore().getId();
        this.orderMenuIds = getMenuIds(order.getMenus());
        this.totalPrice = order.getTotalPrice();
        this.request = order.getRequestMessage();
        this.type = order.getType().getText();
        this.status = order.getStatus().getText();
        this.createdAt = order.getCreatedAt();
    }

    public List<Long> getMenuIds(List<OrderMenu> menus) {
        return menus.stream().map(om -> om.getMenu().getId()).toList();
    }

}
