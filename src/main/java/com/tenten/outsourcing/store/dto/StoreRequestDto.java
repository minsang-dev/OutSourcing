package com.tenten.outsourcing.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private LocalDateTime openTime;

    @NotNull
    private LocalDateTime closeTime;

    @NotNull
    private Integer minAmount;

    @NotBlank
    private String phoneNumber;

    private String storeImageUrl;

}
