package com.tenten.outsourcing.filter;

import static com.tenten.outsourcing.exception.ErrorCode.NO_AUTHOR_OWNER_PAGE;

import com.tenten.outsourcing.common.Role;
import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.exception.NoAuthorizedException;
import com.tenten.outsourcing.user.dto.SessionDto;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
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
      if (isForOwner(requestURI) && !Role.OWNER.equals(sessionDto.getRole())) {
        throw new NoAuthorizedException(NO_AUTHOR_OWNER_PAGE);

      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean isForOwner(String requestUrl){
    return PatternMatchUtils.simpleMatch(FOR_OWNER, requestUrl);
  }
}
