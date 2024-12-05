package com.tenten.outsourcing.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  //InvalidInputException
  //비밀번호 변경 시 이전 비밀번호와 같은 비밀번호를 사용했을 때 출력하는 오류 메시지
  SAME_PASSWORD("비밀번호는 다른 비밀번호를 사용하여야합니다.", HttpStatus.BAD_REQUEST),
  //비밀번호가 틀렸을 때 출력하는 오류 메시지
  WRONG_PASSWORD("비밀 번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
  //아이디 비밀번호가 잘못됨
  DIFFERENT_EMAIL_PASSWORD("이메일 혹은 비밀번호가 잘못되었습니다.", HttpStatus.BAD_REQUEST),
  //탈퇴한 이메일로 가입을 시도할 때 출력하는 오류 메시지
  EMAIL_DELETED("삭제된 이메일 입니다.", HttpStatus.BAD_REQUEST),
  // 이미 삭제된 사용자가 조회 되었을 때 출력하는 오류 메시지
  DELETED_USER("이미 삭제된 회원입니다.", HttpStatus.BAD_REQUEST),
  // 가게 운영 시간 외에 주문을 요청할 경우
  STORE_CLOSED("가게 운영 시간이 아닙니다.", HttpStatus.BAD_REQUEST),
  // 최소 주문 금액을 채우지 않았을 때
  MIN_AMOUNT_NOT_MET("최소 주문 금액을 채워야 합니다.", HttpStatus.BAD_REQUEST),
  INVALID_ORDER_TYPE("올바른 주문 형식이 아닙니다.", HttpStatus.BAD_REQUEST),
  STORE_REGISTRATION_LIMITED("가게를 더 이상 등록할 수 없습니다.", HttpStatus.BAD_REQUEST),

  // NotFoundException
  NOT_FOUND_EMAIL("이메일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NOT_FOUND_MENU("메뉴를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NOT_FOUND_ORDER("주문 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NOT_FOUND_STORE("가게를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),


  // InternalServerException
  //세션이 만료되었을 때 출력하는 오류 메시지
  SESSION_TIMEOUT("세션이 만료되었습니다.", HttpStatus.SERVICE_UNAVAILABLE),
  //잘못된 요청
  WRONG_REQUEST("지원하지 않는 요청입니다.", HttpStatus.SERVICE_UNAVAILABLE),

  // DuplicatedException
  //중복된 이메일로 가일 할 때 출력하는 오류 메시지
  EMAIL_EXIST("중복된 아이디 입니다.", HttpStatus.BAD_REQUEST),

  // NoAuthorizedException
  //로그인이 안 되었을 떄 출력하는 오류 메시지
  NO_SESSION("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
  //이미 로그인이 되어있을 때 출력하는 오류 메시지
  ALREADY_LOGIN("이미 로그인이 되어있습니다.", HttpStatus.UNAUTHORIZED),
  //권한이 없는 사용자가 수정, 삭제를 하려고 할 때
  NO_AUTHOR_CHANGE("수정, 삭제는 작성자만 할 수 있습니다.", HttpStatus.UNAUTHORIZED),
  NO_DELIVERY_ALREADY("배달이 완료된 주문만 리뷰 작성 가능합니다.", HttpStatus.UNAUTHORIZED),
  NO_STORE_OWNER("해당 가게 사장님만 접근 가능합니다.", HttpStatus.UNAUTHORIZED),
  NO_AUTHOR_USER("일반 사용자는 가게를 등록할 수 없습니다.", HttpStatus.UNAUTHORIZED),
  NO_AUTHOR_OWNER_PAGE("오너만 접근 가능한 페이지입니다.", HttpStatus.UNAUTHORIZED),
  NOT_STORE_OWNER("해당 가게 사장님만 접근 가능합니다.", HttpStatus.UNAUTHORIZED);

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
