package com.tenten.outsourcing.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenten.outsourcing.common.BaseEntity;
import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreUpdateRequestDto;
import com.tenten.outsourcing.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String storeImageUrl;

    @Column(length = 25)
    private String name;

    private String address;

    @Column(length = 25)
    private String phoneNumber;

    private Integer minAmount;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime openTime;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime closeTime;

    private LocalDateTime deletedAt;

    public Store(String storeImageUrl,
                 String name,
                 String address,
                 String phoneNumber,
                 Integer minAmount,
                 LocalTime openTime,
                 LocalTime closeTime,
                 User user
    ) {
        this.user = user;
        this.storeImageUrl = storeImageUrl;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.minAmount = minAmount;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void updateStoreInformation(String name, LocalTime openTime, LocalTime closeTime, Integer minAmount, String storeImageUrl) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minAmount = minAmount;
        this.storeImageUrl = storeImageUrl;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public Store(User user, String name, String address, String phoneNumber, Integer minAmount, LocalTime openTime, LocalTime closeTime) {
        this.user = user;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.minAmount = minAmount;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
