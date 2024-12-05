package com.tenten.outsourcing.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

  //커스텀
  @ExceptionHandler
  public ResponseEntity<ExceptionResponseDto> duplicatedException(DuplicatedException e){
    return new ResponseEntity<>(new ExceptionResponseDto(e.getErrorCode()), e.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponseDto> internalServerException(InternalServerException e){
    return new ResponseEntity<>(new ExceptionResponseDto(e.getErrorCode()), e.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponseDto> invalidInputException(InvalidInputException e){
    return new ResponseEntity<>(new ExceptionResponseDto(e.getErrorCode()), e.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponseDto> notFoundException(NotFoundException e){
    return new ResponseEntity<>(new ExceptionResponseDto(e.getErrorCode()), e.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponseDto> noAuthorizedException(NoAuthorizedException e){
    return new ResponseEntity<>(new ExceptionResponseDto(e.getErrorCode()), e.getErrorCode().getHttpStatus());
  }

  //자바
  @ExceptionHandler
  public ResponseEntity<String> constrainViolationException(ConstraintViolationException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

//  @ExceptionHandler
//  public

//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> constraintViolationException(Exception e) {
//    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//  }

}
