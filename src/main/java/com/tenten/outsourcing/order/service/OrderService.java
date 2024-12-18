package com.tenten.outsourcing.order.service;

import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.common.DeliveryType;
import com.tenten.outsourcing.exception.InvalidInputException;
import com.tenten.outsourcing.exception.NoAuthorizedException;
import com.tenten.outsourcing.exception.NotFoundException;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.service.MenuService;
import com.tenten.outsourcing.order.dto.OrderResponseDto;
import com.tenten.outsourcing.order.entity.Order;
import com.tenten.outsourcing.order.repository.OrderRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import static com.tenten.outsourcing.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final MenuService menuService;

    /**
     * 주문 생성 메서드.
     * 최소 주문 금액 불충족 또는 운영 시간 외일 때 생성되지 않음
     *
     * @param userId 로그인한 유저 식별자
     */
    @Transactional
    public OrderResponseDto createOrder(Long menuId, DeliveryType type, String request, Long userId) {

        User findUser = userService.findByIdOrElseThrow(userId);
        Menu findMenu = menuService.findByIdOrElseThrow(menuId);
        Store store = findMenu.getStore();
        if (LocalTime.now().isAfter(store.getCloseTime())
                || LocalTime.now().isBefore(store.getOpenTime())
        ) {
            throw new InvalidInputException(STORE_CLOSED);
        }

        // 최소 주문 금액 불충족시
        if (findMenu.getPrice() < store.getMinAmount()) {
            throw new InvalidInputException(MIN_AMOUNT_NOT_MET);
        }

        Integer totalPrice = findMenu.getPrice();

        Order order = new Order(store, findUser, findMenu, totalPrice, request, type, DeliveryStatus.ACCEPTED);
        Order savedOrder = orderRepository.save(order);
        // 가게 운영 시간이 아닐시


        return new OrderResponseDto(savedOrder);
    }

    /**
     * 주문 상태를 다음 단계로 업데이트. 해당 주문을 받은 점주만 업데이트 가능
     *
     * @param orderId 주문 식별자
     * @param loginId 로그인한 유저 식별자
     */
    @Transactional
    public String updateOrderStatus(Long orderId, Long loginId) {

        User findUser = userService.findByIdOrElseThrow(loginId);
        Order findOrder = findOrderByIdOrElseThrow(orderId);

        // 해당 주문을 받은 점주가 아닌 경우
        if (!findOrder.getStore().getUser().equals(findUser)) {
            throw new NoAuthorizedException(NO_AUTHOR_ORDER);
        }

        DeliveryStatus nextStatus = DeliveryStatus.findNextStatus(findOrder.getStatus());

        findOrder.updateStatus(nextStatus);
        orderRepository.save(findOrder);

        return nextStatus.getText();
    }

    /**
     * 주문 단건 조회. 본인의 주문 내역이거나 주문을 받은 사장일 때만 조회 가능
     *
     * @param loginId 로그인한 유저
     */
    public OrderResponseDto findOrder(Long orderId, Long loginId) {

        Order findOrder = findOrderByIdOrElseThrow(orderId);
        if (findOrder.getUser().getId().equals(loginId) || findOrder.getStore().getUser().getId().equals(loginId)) {
            return new OrderResponseDto(findOrder);
        } else {
            throw new NoAuthorizedException(NO_AUTHOR_ORDER);
        }
    }

    /**
     * 로그인한 유저의 주문 다건 조회
     *
     * @param loginId 로그인한 유저 식별자
     * @param page    페이지 번호
     * @param size    페이지 크기
     */
    public List<OrderResponseDto> findAllOrders(long loginId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<Order> allOrders = orderRepository.findAllOrdersByUserId(loginId, pageable);
        return allOrders.stream().map(OrderResponseDto::new).toList();
    }

    /**
     * 주문 Id 값을 통해 주문을 찾고, 없는 경우 예외 throw
     *
     * @param orderId 주문 식별자
     * @return 주문
     */
    public Order findOrderByIdOrElseThrow(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundException(NOT_FOUND_ORDER)
        );
    }
}
