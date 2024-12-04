package com.tenten.outsourcing.menu.dto;

import lombok.Getter;

@Getter
public class MenuUpdateRequestDto {

    private final String menuName;
    private final String menuPictureUrl;
    private final Integer price;
    private final String password;

    public MenuUpdateRequestDto(String menuName, String menuPictureUrl, Integer price, String password) {
        this.menuName = menuName;
        this.menuPictureUrl = menuPictureUrl;
        this.price = price;
        this.password = password;
    }
}
