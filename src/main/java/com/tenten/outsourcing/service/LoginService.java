package com.tenten.outsourcing.service;

import com.tenten.outsourcing.dto.UserRequestDto;
import com.tenten.outsourcing.dto.UserResponseDto;
import com.tenten.outsourcing.entity.User;
import com.tenten.outsourcing.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final LoginRepository loginRepository;

  public UserResponseDto signUp(UserRequestDto userRequestDto) {
    User user = loginRepository.save(new User(userRequestDto));
     return UserResponseDto.toDto(user);
  }

  public UserResponseDto login(String username, String password) {
//    return new UsersResponseDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getCreatedAt(), user.getUpdatedAt());
    return null;
  }

  public String logout() {
    return null;
  }

}
