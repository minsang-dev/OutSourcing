package com.tenten.outsourcing.service;

import com.tenten.outsourcing.config.PasswordEncoder;
import com.tenten.outsourcing.dto.LoginRequestDto;
import com.tenten.outsourcing.dto.UserRequestDto;
import com.tenten.outsourcing.dto.UserResponseDto;
import com.tenten.outsourcing.entity.User;
import com.tenten.outsourcing.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final LoginRepository loginRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponseDto signUp(UserRequestDto userRequestDto) {
    User user = loginRepository.save(new User(userRequestDto));
     return UserResponseDto.toDto(user);
  }

  public User checkLoginInfo(LoginRequestDto loginRequestDto) {
    User user = findByEmailOrElseThrow(loginRequestDto.getEmail());
    isPasswordMatch(user.getPassword(), loginRequestDto.getPassword());
    return user;
  }
  
  public User findByEmailOrElseThrow(String email) {
    return loginRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("이메일이 없습니다."));
  }

  public void isPasswordMatch(String password, String inputPassword){
    if(!passwordEncoder.matches(inputPassword, password)){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.");
    }
  }

  public User findByIdOrElseThrow(Long userId) {
    return loginRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
  }
}
