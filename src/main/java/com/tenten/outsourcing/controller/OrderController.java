package com.tenten.outsourcing.controller;

import com.tenten.outsourcing.dto.OrderRequestDto;
import com.tenten.outsourcing.dto.OrderResponseDto;
import com.tenten.outsourcing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto dto
            // @SessionAttribute(name = "sessionKey") Long loginId
    ) {

        // TODO: 세션 로그인 구현 후 수정
        OrderResponseDto orderDto = orderService.createOrder(dto, 1L);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }
}
