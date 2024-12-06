package com.tenten.outsourcing.user.dto;

import com.tenten.outsourcing.common.Role;
import com.tenten.outsourcing.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

  private String email;

  private String name;

  private String address;

  private Role role;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static UserResponseDto toDto(User user) {
    return new UserResponseDto(
        user.getEmail(),
        user.getName(),
        user.getAddress(),
        user.getRole(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }
}
