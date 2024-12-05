package com.tenten.outsourcing.store.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreUpdateRequestDto {
    private String name;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    private Integer minAmount;

    private String phoneNumber;

    private String storeImageUrl;
}
