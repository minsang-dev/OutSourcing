package com.tenten.outsourcing.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum DeliveryType {
    DELIVERY("배달"),
    TAKE_OUT("포장");

    private final String text;

    public static Optional<DeliveryType> findTypeByText(String text) {
        for(DeliveryType d :DeliveryType.values()) {
            if(d.getText().equals(text)) {
                return Optional.of(d);
            }
        }
        return Optional.empty();
    }
}
