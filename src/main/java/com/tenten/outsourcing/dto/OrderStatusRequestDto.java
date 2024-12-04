package com.tenten.outsourcing.dto;

import com.tenten.outsourcing.common.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderStatusRequestDto {

    private DeliveryStatus status;
}
