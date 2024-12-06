package com.tenten.outsourcing.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tenten.outsourcing.exception.ErrorCode;
import com.tenten.outsourcing.exception.InvalidInputException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryType {
    DELIVERY("배달"),
    TAKE_OUT("포장");

    private final String text;

    /**
     * String으로 들어온 요청값을 {@link DeliveryType}으로 변경
     *
     * @param type 입력받은 type
     */
    @JsonCreator
    public DeliveryType from(String type) {
        for (DeliveryType dt : DeliveryType.values()) {
            if (dt.getText().equals(type)) {
                return dt;
            }
        }
        throw new InvalidInputException(ErrorCode.INVALID_ORDER_TYPE);
    }
}
