package com.tenten.outsourcing.controller;

import com.tenten.outsourcing.common.PagingResponseDto;
import com.tenten.outsourcing.dto.OrderRequestDto;
import com.tenten.outsourcing.dto.OrderResponseDto;
import com.tenten.outsourcing.dto.OrderStatusRequestDto;
import com.tenten.outsourcing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // TODO: 인증 인가 구현
        OrderResponseDto orderDto = orderService.createOrder(dto, 1L);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequestDto dto
            // @SessionAttribute(name = "sessionKey") Long loginId
    ) {
        // TODO: 인가 구현
        orderService.updateOrderStatus(orderId, 2L, dto.getStatus());
        return ResponseEntity.ok().body("상태가 변경되었습니다: " + dto.getStatus().getText());
    }

    /**
     * 로그인한 유저의 주문 단건 조회
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findOrder(
            @PathVariable Long orderId
            // @SessionAttribute(name = "sessionKey) Long loginId
    ) {

        // TODO: 인가 구현
        OrderResponseDto orderDto = orderService.findOrder(orderId, 1L);
        return ResponseEntity.ok().body(orderDto);
    }

    /**
     * 로그인한 유저의 주문 다건 조회
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     */
    @GetMapping
    public ResponseEntity<PagingResponseDto> findAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            // @SessionAttribute(name = "sessionKey) Long loginId
    ) {

        // TODO: 인가 구현
        List<OrderResponseDto> allOrdersDto = orderService.findAllOrders(1L, page, size);
        return ResponseEntity.ok().body(new PagingResponseDto(page, size, allOrdersDto));
    }
}
