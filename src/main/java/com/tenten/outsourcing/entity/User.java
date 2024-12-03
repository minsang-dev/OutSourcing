package com.tenten.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenten.outsourcing.common.BaseEntity;
import com.tenten.outsourcing.config.PasswordEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, length = 100)
  private String email;

  @Convert(converter = PasswordEncoder.class)
  @Column(length = 100)
  private String password;

  @Column(length = 10)
  private String name;

  @Column(length = 100)
  private String address;

//  private Auth auth;

  @JsonFormat(pattern = "yy:MM:dd hh:mm:ss")
  private LocalDate deletedAt;

}
