package com.tenten.outsourcing.entity;

import com.tenten.outsourcing.common.BaseEntity;
import com.tenten.outsourcing.common.DeliveryStatus;
import com.tenten.outsourcing.common.DeliveryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer totalPrice;

    private String requestMessage;

    private DeliveryType type;

    private DeliveryStatus status;

    public Order(User user, Menu menu, Integer totalPrice, String requestMessage, DeliveryType type, DeliveryStatus status) {
        this.user = user;
        this.menu = menu;
        this.totalPrice = totalPrice;
        this.requestMessage = requestMessage;
        this.type = type;
        this.status = status;
    }

}
