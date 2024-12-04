package com.tenten.outsourcing.user.dto;

import com.tenten.outsourcing.common.Auth;
import lombok.Getter;

@Getter
public class UserRequestDto {

  private String email;

  private String password;

  private String name;

  private String address;

  private Auth auth;

}
