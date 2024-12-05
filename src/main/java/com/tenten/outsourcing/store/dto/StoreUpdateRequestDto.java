package com.tenten.outsourcing.store.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class StoreUpdateRequestDto {
    private String name;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer minAmount;

    private String phoneNumber;

    private String storeImageUrl;
}
