package com.tenten.outsourcing.filter;

import com.tenten.outsourcing.common.Auth;
import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.user.dto.SessionDto;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class AuthFilter implements Filter {

  private final String[] FOR_OWNER = {
      "/api/test/owner"
  };

  @Override
  public void doFilter(
      ServletRequest servletRequest,
      ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    String requestURI = httpRequest.getRequestURI();
    HttpSession session = httpRequest.getSession(false);

    if(session !=null) {
      SessionDto sessionDto = (SessionDto) session.getAttribute(LoginStatus.LOGIN_USER);
      if(sessionDto == null) {
        return;
      }
      if (isForOwner(requestURI) && !Auth.OWNER.equals(sessionDto.getAuth())) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.getWriter().write("오너만 접근 가능한 페이지입니다.");
        return;
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean isForOwner(String requestUrl){
    return PatternMatchUtils.simpleMatch(FOR_OWNER, requestUrl);
  }
}
