package com.tenten.outsourcing.store.dto;

import com.tenten.outsourcing.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreUpdateResponseDto {
    private String name;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    private Integer minAmount;

    private String phoneNumber;

    private String storeImageUrl;

    public StoreUpdateResponseDto(Store store) {
        this.name = store.getName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minAmount = store.getMinAmount();
        this.phoneNumber = store.getPhoneNumber();
        this.storeImageUrl = store.getStoreImageUrl();
    }
}
