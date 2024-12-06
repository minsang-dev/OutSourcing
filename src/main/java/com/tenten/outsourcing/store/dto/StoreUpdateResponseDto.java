package com.tenten.outsourcing.store.dto;

import com.tenten.outsourcing.store.entity.Store;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreUpdateResponseDto {
    private String name;

    private String address;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer minAmount;

    private String phoneNumber;

    private String storeImageUrl;

    public StoreUpdateResponseDto(Store store) {
        this.name = store.getName();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minAmount = store.getMinAmount();
        this.phoneNumber = store.getPhoneNumber();
        this.storeImageUrl = store.getStoreImageUrl();
    }
}
