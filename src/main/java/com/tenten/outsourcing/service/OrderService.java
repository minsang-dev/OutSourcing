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
     * 주문 상태 업데이트. 사장 권한을 가진 유저만 가능
     *
     * @param orderId 주문 식별자
     * @param loginId 로그인한 유저 식별자
     * @param status  변경할 주문 처리 상태값
     */
    @Transactional
    public void updateOrderStatus(Long orderId, Long loginId, DeliveryStatus status) {

        User findUser = userRepository.findById(loginId).orElseThrow();
        // TODO: 로그인한 유저에게 점주 권한이 없을 때
        /*if(!findUser.getAuth().equals(Auth.점주)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }*/
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        // TODO: 로그인한 유저가 점주이나, 해당 주문을 받은 가게의 점주가 아닐 때
        /*if(!findOrder.getStore().getUser().equals(findUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }*/

        findOrder.updateStatus(status);
        orderRepository.save(findOrder);
    }

    /**
     * 주문 단건 조회
     */
    public OrderResponseDto findOrder(Long orderId) {

        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        return new OrderResponseDto(findOrder);
    }
}
