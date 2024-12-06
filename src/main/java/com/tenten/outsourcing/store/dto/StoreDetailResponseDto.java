package com.tenten.outsourcing.store.dto;

import com.tenten.outsourcing.menu.dto.MenuResponseDto;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.store.entity.Store;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreDetailResponseDto {
    private String name;

    private String address;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer minAmount;

    private String phoneNumber;

    private String storeImageUrl;

    private List<MenuResponseDto> menus;

    public StoreDetailResponseDto(Store store, List<MenuResponseDto> menus) {
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
