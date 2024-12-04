package com.tenten.outsourcing.user.dto;

import com.tenten.outsourcing.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

  private String email;

  private String name;

  private String address;

  public static UserResponseDto toDto(User user) {
    return new UserResponseDto(user.getEmail(),
        user.getName(),
        user.getAddress());
  }
}
