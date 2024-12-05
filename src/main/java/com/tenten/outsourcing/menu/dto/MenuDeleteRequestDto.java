package com.tenten.outsourcing.menu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuDeleteRequestDto {

    @NotNull
    private String password;

    public MenuDeleteRequestDto(String password) {
        this.password = password;
    }
}
