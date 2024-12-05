package com.tenten.outsourcing.bucket.dto;

import com.tenten.outsourcing.bucket.entity.Bucket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BucketResponseDto {

    private final Long menuId;

    private final int count;

    public BucketResponseDto(Bucket bucket) {
        this.menuId = bucket.getMenuId();
        this.count = bucket.getCount();
    }
}
