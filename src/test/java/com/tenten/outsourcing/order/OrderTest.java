package com.tenten.outsourcing.order;


import com.tenten.outsourcing.common.Role;
import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.common.DeliveryType;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.order.entity.Order;
import com.tenten.outsourcing.order.repository.OrderRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    private static Order order;
    private static User owner;
    private static User client;
    private static Menu menu;
    private static Store store;

     @BeforeEach
     void setup(){
         owner = new User(1L, "email@gmail.com", "1234", "owner", "address", Role.OWNER, null);
         client = new User(2L, "email2@gmail.com", "1234", "user", "address", Role.USER, null);
         menu = new Menu(1L, store, "menuNmae", null, 10000, null);
         menu = new Menu(2L, store, "menuNmae", null, 20000, null);
         order = new Order(store, client, menu, 15000, "요청사항", DeliveryType.DELIVERY, DeliveryStatus.ACCEPTED);
     }

     @Test
    public void saveOrderTest(){
         Order saveOrder = orderRepository.save(order);

        assertThat(saveOrder).isNotNull();
     }
}
