package com.tenten.outsourcing.order.dto;

import com.tenten.outsourcing.common.DeliveryType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull
    private Long storeId;

    private String request;

    @NotNull
    private DeliveryType type;
}
