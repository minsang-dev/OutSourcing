package com.tenten.outsourcing.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BucketRequestDto {

    @NotNull
    private Long storeId;

    @NotNull
    private Long menuId;
}
