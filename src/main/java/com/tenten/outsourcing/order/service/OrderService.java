package com.tenten.outsourcing.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.common.DeliveryType;
import com.tenten.outsourcing.common.Mapper;
import com.tenten.outsourcing.exception.InvalidInputException;
import com.tenten.outsourcing.exception.NoAuthorizedException;
import com.tenten.outsourcing.exception.NotFoundException;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.service.MenuService;
import com.tenten.outsourcing.order.dto.OrderResponseDto;
import com.tenten.outsourcing.order.entity.Bucket;
import com.tenten.outsourcing.order.entity.BucketMenu;
import com.tenten.outsourcing.order.entity.Order;
import com.tenten.outsourcing.order.entity.OrderMenu;
import com.tenten.outsourcing.order.repository.OrderRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.service.StoreService;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.tenten.outsourcing.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final MenuService menuService;
    private final StoreService storeService;
    private final BucketService bucketService;

    /**
     * 주문 생성 메서드.
     * 최소 주문 금액 불충족 또는 운영 시간 외일 때 생성되지 않음
     *
     * @param userId 로그인한 유저 식별자
     */
    @Transactional
    public OrderResponseDto createOrder(String cookieValue, DeliveryType type, String request, Long userId) {

        Bucket bucket;
        User findUser = userService.findByIdOrElseThrow(userId);

        try {
            bucket = Mapper.jsonStringToBucket(cookieValue);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Store store = storeService.findById(bucket.getStoreId());
        // 가게 운영 시간이 아닐시
        if (LocalTime.now().isAfter(store.getCloseTime())
                || LocalTime.now().isBefore(store.getOpenTime())
        ) {
            throw new InvalidInputException(STORE_CLOSED);
        }

        // 주문한 메뉴 목록을 찾고 bucket에서 삭제된 메뉴를 제거
        List<Menu> menuList = menuService.findOrderedMenu(bucket.getBucketMenus());
        bucket.removeDeletedMenus(menuList);

        for (BucketMenu bm : bucket.getBucketMenus()) {
            for (Menu m : menuList) {
                if (bm.getMenuId().equals(m.getId())) {
                    bm.placeMenu(m);
                }
            }
        }

        int totalPrice = bucket.getBucketMenus().stream().mapToInt(bm -> bm.getMenu().getPrice() * bm.getCount()).sum();
        log.info(String.valueOf(totalPrice));
        // 최소 주문 금액 불충족시
        if (totalPrice < store.getMinAmount()) {
            throw new InvalidInputException(MIN_AMOUNT_NOT_MET);
        }

        Order order = new Order(store, findUser, totalPrice, request, type, DeliveryStatus.ACCEPTED);

        List<OrderMenu> menus = new ArrayList<>();
        for (BucketMenu bm : bucket.getBucketMenus()) {
            menus.add(new OrderMenu(order, bm.getMenu(), bm.getCount()));
        }
        order.confirmMenus(menus);
        Order savedOrder = orderRepository.save(order);

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
