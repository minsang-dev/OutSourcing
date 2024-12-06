package com.tenten.outsourcing.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuDeleteRequestDto {

    @NotBlank
    private String password;

    public MenuDeleteRequestDto(String password) {
        this.password = password;
    }
}
