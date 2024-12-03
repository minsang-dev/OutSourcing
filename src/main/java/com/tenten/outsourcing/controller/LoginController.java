package com.tenten.outsourcing.controller;

import com.tenten.outsourcing.dto.UserRequestDto;
import com.tenten.outsourcing.dto.UserResponseDto;
import com.tenten.outsourcing.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/sign-up")
  public ResponseEntity<UserResponseDto> signUp(
      @RequestParam("username") String username
      ){
    return null;
  }

  @PostMapping("/login")
  public ResponseEntity<UserResponseDto> login(
      @RequestBody UserRequestDto userRequestDto
  ){
    return null;
  }

  @PostMapping("/logout")
  public ResponseEntity<UserResponseDto> logout(
      @RequestParam Long id,
      HttpServletRequest request
  ){
    return null;
  }

}
