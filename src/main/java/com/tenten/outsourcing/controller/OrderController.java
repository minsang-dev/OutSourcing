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

    /**
     * 주문 생성
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto dto
            // @SessionAttribute(name = "sessionKey") Long loginId
    ) {
        // TODO: 세션 로그인 구현 후 수정
        OrderResponseDto orderDto = orderService.createOrder(dto, 1L);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    /**
     * 주문 단건 조회
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<?> findOrder(
            @PathVariable Long orderId
    ) {
        OrderResponseDto orderDto = orderService.findOrder(orderId);
        return ResponseEntity.ok().body(orderDto);
    }
}
