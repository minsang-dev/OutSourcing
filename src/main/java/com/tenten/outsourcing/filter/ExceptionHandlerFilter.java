package com.tenten.outsourcing.filter;

import com.tenten.outsourcing.exception.ErrorCode;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try{
      filterChain.doFilter(request, response);
    }catch(Exception e){
      setExceptionResponse(request, response, ErrorCode.NO_SESSION);
    }
  }

  private void setExceptionResponse(
      HttpServletRequest request,
      HttpServletResponse response,
      ErrorCode errorCode) {
    response.setStatus(errorCode.getHttpStatus().value());
    response.setContentType("text/html;charset=UTF-8");
  }
}
