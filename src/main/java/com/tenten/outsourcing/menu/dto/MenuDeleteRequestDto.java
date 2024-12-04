package com.tenten.outsourcing.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuDeleteRequestDto {

    private String password;

    public MenuDeleteRequestDto(String password) {
        this.password = password;
    }
}
