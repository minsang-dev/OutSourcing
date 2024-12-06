package com.tenten.outsourcing;

import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.common.DeliveryType;
import com.tenten.outsourcing.common.Role;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.repository.MenuRepository;
import com.tenten.outsourcing.order.entity.Order;
import com.tenten.outsourcing.order.repository.OrderRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.repository.StoreRepository;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalTime;


@Slf4j
@Component
@Profile("dev")
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        User owner = new User("test@gmail.com", "test!1234", "가게 사장", "사장주소", Role.OWNER);
        User user = new User("test2@gmail.com", "test!1234", "손님", "손님주소", Role.USER);

        Store store = new Store(owner, "초코비 가게", "짱구네집", "011-5875-5221", 5000, LocalTime.of(0, 1), LocalTime.of(20, 0));

        Menu menu = new Menu(store, "초코비", 8000);

        Order order = new Order(store, user, menu, 100, "content", DeliveryType.DELIVERY, DeliveryStatus.DELIVERED);

        userRepository.save(owner);
        userRepository.save(user);
        storeRepository.save(store);
        menuRepository.save(menu);
        orderRepository.save(order);

        log.info("======= add column =======");
    }
}
