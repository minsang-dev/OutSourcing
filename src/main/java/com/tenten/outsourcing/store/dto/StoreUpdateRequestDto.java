package com.tenten.outsourcing.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreUpdateRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    @NotNull
    private Integer minAmount;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String storeImageUrl;
}
