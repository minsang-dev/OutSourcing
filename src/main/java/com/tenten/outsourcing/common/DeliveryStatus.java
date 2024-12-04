package com.tenten.outsourcing.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    // 주문 완료 → 주문 수락 → 조리 중 → 조리 완료 → 배달 중 → 배달 완료
    ORDERED("주문 완료"),
    ACCEPTED("주문 수락"),
    PREPARING("조리 중"),
    READY("조리 완료"),
    DELIVERING("배달 중"),
    DELIVERED("배달 완료");

    private final String text;
}
