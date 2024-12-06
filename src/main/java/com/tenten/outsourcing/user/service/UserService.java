package com.tenten.outsourcing.user.service;

import static com.tenten.outsourcing.exception.ErrorCode.DELETED_USER;
import static com.tenten.outsourcing.exception.ErrorCode.EMAIL_EXIST;
import static com.tenten.outsourcing.exception.ErrorCode.NOT_FOUND_EMAIL;
import static com.tenten.outsourcing.exception.ErrorCode.SESSION_TIMEOUT;
import static com.tenten.outsourcing.exception.ErrorCode.WRONG_PASSWORD;

import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.config.PasswordEncoder;
import com.tenten.outsourcing.exception.DuplicatedException;
import com.tenten.outsourcing.exception.InternalServerException;
import com.tenten.outsourcing.exception.InvalidInputException;
import com.tenten.outsourcing.exception.NotFoundException;
import com.tenten.outsourcing.user.dto.LoginRequestDto;
import com.tenten.outsourcing.user.dto.SessionDto;
import com.tenten.outsourcing.user.dto.UserRequestDto;
import com.tenten.outsourcing.user.dto.UserResponseDto;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    checkEmailDulicated(userRequestDto.getEmail());
    User user = userRepository.save(new User(userRequestDto));
     return UserResponseDto.toDto(user);
  }

  public User checkLoginInfo(LoginRequestDto loginRequestDto) {
    checkDeletedUserByEmail(loginRequestDto.getEmail());
    User user = findByEmailOrElseThrow(loginRequestDto.getEmail());
    checkPasswordMatch(user.getPassword(), loginRequestDto.getPassword());
    return user;
  }

  public void deleteUser(Long id, String password) {
    User user = findByIdOrElseThrow(id);
    checkPasswordMatch(user.getPassword(), password);
    user.setDeletedAt(LocalDateTime.now());
    userRepository.save(user);
  }
  
  public User findByEmailOrElseThrow(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_EMAIL));
  }

  public void checkPasswordMatch(String password, String inputPassword){
    if(!passwordEncoder.matches(inputPassword, password)){
      throw new InvalidInputException(WRONG_PASSWORD);
    }
  }

  public User findByIdOrElseThrow(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_EMAIL));
  }

  public void checkDeletedUserByEmail(String email) {
    User user = findByEmailOrElseThrow(email);
    if(user.getDeletedAt() != null){
      throw new InvalidInputException(DELETED_USER);
    }
  }

  private void checkEmailDulicated(String email){
    if(userRepository.existsByEmail(email)){
      throw new DuplicatedException(EMAIL_EXIST);
    }
  }

}
