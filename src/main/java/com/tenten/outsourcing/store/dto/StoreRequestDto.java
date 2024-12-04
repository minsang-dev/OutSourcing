package com.tenten.outsourcing.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private LocalDateTime openTime;

    @NotBlank
    private LocalDateTime closeTime;

    @NotBlank
    private Integer minAmount;

    @NotBlank
    private String phoneNumber;

    private String storeImageUrl;

}
