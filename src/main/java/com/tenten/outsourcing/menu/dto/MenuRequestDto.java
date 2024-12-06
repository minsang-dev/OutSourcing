package com.tenten.outsourcing.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    @NotBlank
    private final String menuName;

    private final String menuPictureUrl;

    @NotNull
    private final Integer price;

    public MenuRequestDto(String menuName, String menuPictureUrl, Integer price) {
        this.menuName = menuName;
        this.menuPictureUrl = menuPictureUrl;
        this.price = price;
    }
}
