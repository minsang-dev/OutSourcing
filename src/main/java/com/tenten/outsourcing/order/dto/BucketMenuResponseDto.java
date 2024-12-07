package com.tenten.outsourcing.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BucketMenuResponseDto {

    private final Long menuId;

    private final int count;
}
