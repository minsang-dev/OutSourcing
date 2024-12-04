package com.tenten.outsourcing.order.controller;

import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.common.PagingResponseDto;
import com.tenten.outsourcing.order.dto.OrderRequestDto;
import com.tenten.outsourcing.order.dto.OrderResponseDto;
import com.tenten.outsourcing.order.dto.OrderStatusRequestDto;
import com.tenten.outsourcing.order.service.OrderService;
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
            @RequestBody OrderRequestDto dto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) Long loginId
    ) {

        OrderResponseDto orderDto = orderService.createOrder(dto, loginId);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    /**
     * 주문 현황 업데이트
     * 해당 주문을 받은 점주만 업데이트 가능
     *
     * @param orderId 주문 식별자
     * @param loginId 현재 로그인한 유저 식별자
     */
    @PatchMapping("/status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequestDto dto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) Long loginId
    ) {

        orderService.updateOrderStatus(orderId, loginId, dto.getStatus());
        return ResponseEntity.ok().body("상태가 변경되었습니다: " + dto.getStatus().getText());
    }

    /**
     * 로그인한 유저의 주문 단건 조회
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findOrder(
            @PathVariable Long orderId,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) Long loginId
    ) {

        OrderResponseDto orderDto = orderService.findOrder(orderId, loginId);
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
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) Long loginId
    ) {

        List<OrderResponseDto> allOrdersDto = orderService.findAllOrders(loginId, page, size);
        return ResponseEntity.ok().body(new PagingResponseDto(page, size, allOrdersDto));
    }
}
