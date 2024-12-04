package com.tenten.outsourcing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidInputException extends RuntimeException {

  private final ErrorCode errorCode;
}
