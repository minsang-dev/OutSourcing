package com.tenten.outsourcing.store.dto;

import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class StoreDetailResponseDto {
    private String name;

    private String address;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    private Integer minAmount;

    private String phoneNumber;

    private String storeImageUrl;

    private List<Menu> menus;

    public StoreDetailResponseDto(Store store, List<Menu> menus) {
        this.name = store.getName();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minAmount = store.getMinAmount();
        this.phoneNumber = store.getPhoneNumber();
        this.storeImageUrl = store.getStoreImageUrl();
        this.menus = menus;
    }
}
