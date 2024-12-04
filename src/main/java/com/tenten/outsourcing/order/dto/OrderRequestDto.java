package com.tenten.outsourcing.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {

    private Long menuId;

    private String request;

    private String type;
}
