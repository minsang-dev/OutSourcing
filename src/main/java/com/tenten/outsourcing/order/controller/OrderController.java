package com.tenten.outsourcing.order.controller;

import com.tenten.outsourcing.common.CookieName;
import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.common.PagingResponseDto;
import com.tenten.outsourcing.order.dto.OrderRequestDto;
import com.tenten.outsourcing.order.dto.OrderResponseDto;
import com.tenten.outsourcing.order.service.OrderService;
import com.tenten.outsourcing.user.dto.SessionDto;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
            @RequestBody @Valid OrderRequestDto dto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session,
            @CookieValue(name = CookieName.Bucket) Cookie cookie
    ) {

        OrderResponseDto ordersDto = orderService.createOrder(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8), dto.getType(), dto.getRequest(), session.getId());
        return new ResponseEntity<>(ordersDto, HttpStatus.CREATED);
    }

    /**
     * 주문 현황 업데이트
     * 해당 주문을 받은 점주만 순차적으로 업데이트 가능
     *
     * @param orderId 주문 식별자
     * @param session 현재 로그인한 유저 세션
     */
    @PatchMapping("/status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {

        String status = orderService.updateOrderStatus(orderId, session.getId());
        return ResponseEntity.ok().body("상태가 변경되었습니다: " + status);
    }

    /**
     * 로그인한 유저의 주문 단건 조회
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findOrder(
            @PathVariable Long orderId,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {

        OrderResponseDto orderDto = orderService.findOrder(orderId, session.getId());
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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {

        List<OrderResponseDto> allOrdersDto = orderService.findAllOrders(session.getId(), page, size);
        return ResponseEntity.ok().body(new PagingResponseDto(page, size, allOrdersDto));
    }
}
