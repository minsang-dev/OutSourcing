package com.tenten.outsourcing.store.dto;

import com.tenten.outsourcing.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class StoreResponseDto {

    private Long storeId;

    private Long userId;

    private String name;

    private String address;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer minAmount;

    private String phoneNumber;

    private String storeImageUrl;

    private LocalDateTime createAt;

    public StoreResponseDto(Store store) {
        this.storeId = store.getId();
        this.userId = store.getUser().getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minAmount = store.getMinAmount();
        this.phoneNumber = store.getPhoneNumber();
        this.storeImageUrl = store.getStoreImageUrl();
        this.createAt = store.getCreatedAt();
    }
}
