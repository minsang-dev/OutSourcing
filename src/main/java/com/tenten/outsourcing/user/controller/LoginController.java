package com.tenten.outsourcing.user.controller;

import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.user.dto.LoginRequestDto;
import com.tenten.outsourcing.user.dto.UserRequestDto;
import com.tenten.outsourcing.user.dto.UserResponseDto;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;

  @PostMapping("/sign-up")
  public ResponseEntity<UserResponseDto> signUp(
      @RequestBody UserRequestDto userRequestDto
      ){
    return ResponseEntity.ok().body(userService.signUp(userRequestDto));
  }

  @PostMapping("/login")
//  public ResponseEntity<UserResponseDto> login(
  public ResponseEntity<String> login(
      @RequestBody LoginRequestDto loginRequestDto,
      HttpServletRequest request
  ){

    User user = userService.checkLoginInfo(loginRequestDto);

    HttpSession session = request.getSession();

    session.setAttribute(LoginStatus.LOGIN_USER, user.getId());

    return ResponseEntity.ok().body("로그인 성공");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(
      HttpServletRequest request
  ){
    HttpSession session = request.getSession();
    if(session != null){
      session.invalidate();
    }
    return ResponseEntity.ok().body("로그아웃 되었습니다.");
  }

  @DeleteMapping("/users")
  public ResponseEntity<String> deleteUser(
      HttpServletRequest request
  ){
    HttpSession session = request.getSession();
    return ResponseEntity.ok().body("삭제 되었습니다.");
  }

  @GetMapping("/test")
  public ResponseEntity<UserResponseDto> test(

  ){
    return null;
  }

  @GetMapping("/test/owner")
  public ResponseEntity<UserResponseDto> testOwner(

  ){
    return null;
  }
}
