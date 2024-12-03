package com.tenten.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenten.outsourcing.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store extends BaseEntity {

  @Id
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
  private LocalDateTime openTime;
//  private LocalTime openTime;

  @JsonFormat(pattern = "hh:mm:ss")
  private LocalDateTime closeTime;

  private LocalDateTime deletedAt;

}
