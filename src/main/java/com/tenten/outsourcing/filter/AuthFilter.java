package com.tenten.outsourcing.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest,
      ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

  }
}
