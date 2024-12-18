package com.tenten.outsourcing.review.entity;

import com.tenten.outsourcing.common.BaseEntity;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.order.entity.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Min(value = 0)
    @Max(value = 5)
    private Integer rating;

    private String content;

    private Long parents;

    public Review(User user, Store store, Order order, Integer rating, String content) {
        this.user = user;
        this.store = store;
        this.order = order;
        this.rating = rating;
        this.content = content;
        this.parents = null;
    }

    public Review(User user, Store store, Order order, String content, Long parents) {
        this.user = user;
        this.store = store;
        this.order = order;
        this.rating = null;
        this.content = content;
        this.parents = parents;
    }
}
