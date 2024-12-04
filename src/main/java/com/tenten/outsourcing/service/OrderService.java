package com.tenten.outsourcing.service;

import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.common.DeliveryType;
import com.tenten.outsourcing.dto.OrderRequestDto;
import com.tenten.outsourcing.dto.OrderResponseDto;
import com.tenten.outsourcing.entity.Menu;
import com.tenten.outsourcing.entity.Order;
import com.tenten.outsourcing.entity.User;
import com.tenten.outsourcing.repository.MenuRepository;
import com.tenten.outsourcing.repository.OrderRepository;
import com.tenten.outsourcing.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    /**
     * 주문 생성
     *
     * @param userId 로그인한 유저 식별자
     */
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto, Long userId) {

        User findUser = userRepository.findById(userId).orElseThrow();
        Menu findMenu = menuRepository.findById(dto.getMenuId()).orElseThrow();

        Integer totalPrice = findMenu.getPrice();
        DeliveryType type = DeliveryType.findTypeByText(dto.getType()).orElseThrow();

        Order order = new Order(findUser, findMenu, totalPrice, dto.getRequest(), type, DeliveryStatus.ACCEPTED);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);
    }

    /**
     * 주문 단건 조회
     */
    public OrderResponseDto findOrder(Long orderId) {

        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        return new OrderResponseDto(findOrder);
    }
}
