package com.tenten.outsourcing.entity;

import com.tenten.outsourcing.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "menu") // 테이블 이름 매핑
public class Menu extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "store_id", nullable = false)
  private Store store; // 가게 (ManyToOne 관계))

  @Column(length = 25)
  private String menuName;

  private String menuPictureUrl;

  @Min(value = 0)
  private Integer price;

  private LocalDateTime deletedAt;

  public Menu() { }

  public Menu(Store store, String menuName, String menuPictureUrl, Integer price) {
    this.store = store;
    this.menuName = menuName;
    this.menuPictureUrl = menuPictureUrl;
  }

  public Menu(String menuName, String menuPictureUrl, Integer price, User findUser) {

  }
}
