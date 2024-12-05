package com.tenten.outsourcing.user.dto;

import com.tenten.outsourcing.common.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionDto {

  private Long id;

  private Role role;

}
