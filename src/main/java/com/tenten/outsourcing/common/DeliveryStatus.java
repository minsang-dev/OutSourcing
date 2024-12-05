package com.tenten.outsourcing.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    // 주문 완료 → 주문 수락 → 조리 중 → 조리 완료 → 배달 중 → 배달 완료
    ORDERED(0, "주문 완료"),
    ACCEPTED(1, "주문 수락"),
    PREPARING(2, "조리 중"),
    READY(3, "조리 완료"),
    DELIVERING(4, "배달 중"),
    DELIVERED(5, "배달 완료");

    private final int value;
    private final String text;

    /**
     * 다음 단계의 DeliveryStatus를 반환
     *
     * @param status 현재 단계
     */
    public static DeliveryStatus findNextStatus(DeliveryStatus status) {
        int nextValue = status.getValue() + 1;
        for (DeliveryStatus ds : DeliveryStatus.values()) {
            if (ds.getValue() == nextValue) {
                return ds;
            }
        }
        // next에 해당하는 status가 없는 경우 == 최종 단계였을 경우
        return status;
    }
}
