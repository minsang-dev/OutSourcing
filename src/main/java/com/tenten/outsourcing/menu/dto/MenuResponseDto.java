package com.tenten.outsourcing.menu.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class MenuResponseDto {
    private final Long id;
    private final Long storeId;
    private final String menuName;
    private final String menuPictureUrl;
    private final Integer price;
    private final LocalDateTime createdAt;

    public MenuResponseDto(Long id, Long storeId, String menuName, String menuPictureUrl, Integer price, LocalDateTime createdAt) {
        this.id = id;
        this.storeId = storeId;
        this.menuName = menuName;
        this.menuPictureUrl = menuPictureUrl;
        this.price = price;
        this.createdAt = createdAt;
    }
}
