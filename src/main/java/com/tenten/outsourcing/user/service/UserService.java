package com.tenten.outsourcing.user.service;

import com.tenten.outsourcing.config.PasswordEncoder;
import com.tenten.outsourcing.user.dto.LoginRequestDto;
import com.tenten.outsourcing.user.dto.UserRequestDto;
import com.tenten.outsourcing.user.dto.UserResponseDto;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponseDto signUp(UserRequestDto userRequestDto) {
    User user = userRepository.save(new User(userRequestDto));
     return UserResponseDto.toDto(user);
  }

  public User checkLoginInfo(LoginRequestDto loginRequestDto) {
    User user = findByEmailOrElseThrow(loginRequestDto.getEmail());
    isPasswordMatch(user.getPassword(), loginRequestDto.getPassword());
    return user;
  }
  
  public User findByEmailOrElseThrow(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("이메일이 없습니다."));
  }

  public void isPasswordMatch(String password, String inputPassword){
    if(!passwordEncoder.matches(inputPassword, password)){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.");
    }
  }

  public User findByIdOrElseThrow(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
  }
}
