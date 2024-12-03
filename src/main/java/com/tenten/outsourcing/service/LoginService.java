package com.tenten.outsourcing.service;

import com.tenten.outsourcing.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

  @Transactional
  public UserResponseDto login(String username, String password) {
    UserResponseDto user = new UserResponseDto();

    return user;
  }

}
