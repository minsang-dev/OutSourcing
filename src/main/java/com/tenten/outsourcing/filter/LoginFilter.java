package com.tenten.outsourcing.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class LoginFilter implements Filter {

  private static final String[] WHITELIST = {
    "/api/login", "/api/logout", "/api/sign-up"
  };

  @Override
  public void doFilter(
      ServletRequest servletRequest,
      ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    String requestURI = httpRequest.getRequestURI();

    log.info("request URL = " + requestURI);

    if(!isWhiteList(requestURI)) {
      HttpServletResponse response = (HttpServletResponse) servletResponse;
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      servletResponse.setCharacterEncoding("UTF-8");
      servletResponse.getWriter().write("로그인이 필요한 페이지입니다.");

    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean isWhiteList(String requestUrl){
    return PatternMatchUtils.simpleMatch(WHITELIST, requestUrl);
  }
}