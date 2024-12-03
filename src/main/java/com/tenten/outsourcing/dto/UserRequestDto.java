package com.tenten.outsourcing.dto;

import com.tenten.outsourcing.config.PasswordEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.Getter;

@Getter
public class UserRequestDto {

  private String email;

  private String password;

  private String name;

  private String address;

}
