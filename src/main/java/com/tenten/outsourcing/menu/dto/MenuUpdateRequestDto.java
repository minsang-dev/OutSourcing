package com.tenten.outsourcing.menu.dto;

import lombok.Getter;

@Getter
public class MenuUpdateRequestDto {

    private final String menuName;
    private final String menuPictureUrl;
    private final Integer price;

    public MenuUpdateRequestDto(String menuName, String menuPictureUrl, Integer price) {
        this.menuName = menuName;
        this.menuPictureUrl = menuPictureUrl;
        this.price = price;
    }
}
