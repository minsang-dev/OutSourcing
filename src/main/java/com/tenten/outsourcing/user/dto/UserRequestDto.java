package com.tenten.outsourcing.user.dto;

import com.tenten.outsourcing.common.Auth;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {

  @NotBlank(message = "이메일은 필수값입니다.")
  @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9.-]+$", message = "이메일 형식이 일치하지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수값입니다.")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소 8글자, 영어, 숫자, 특수문자가 최소 1개씩 필요합니다.")
  private String password;

  @NotBlank(message = "이름은 필수값입니다.")
  private String name;

  @NotBlank(message = "주소는 필수값입니다.")
  private String address;

  private Auth auth;

}
