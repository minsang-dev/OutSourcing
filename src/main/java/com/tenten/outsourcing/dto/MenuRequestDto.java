package com.tenten.outsourcing.dto;

import lombok.Getter;

@Getter
public class MenuRequestDto {

    private final String menuName;
    private final String menuPictureUrl;
    private final Integer price;

    public MenuRequestDto(String menuName, String menuPictureUrl, Integer price) {
        this.menuName = menuName;
        this.menuPictureUrl = menuPictureUrl;
        this.price = price;
    }
}
