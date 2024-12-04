package com.tenten.outsourcing.menu.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuUpdateResponseDto {

    private final Long StoreId;
    private final Long menuId;
    private final String menuName;
    private final String menuPictureUrl;
    private final Integer price;
    private final LocalDateTime updatedAt;

    public MenuUpdateResponseDto(Long storeId, Long menuId, String menuName, String menuPictureUrl, Integer price, LocalDateTime updatedAt) {
        StoreId = storeId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPictureUrl = menuPictureUrl;
        this.price = price;
        this.updatedAt = updatedAt;
    }
}
