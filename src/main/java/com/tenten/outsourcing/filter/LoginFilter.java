package com.tenten.outsourcing.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.exception.ErrorCode;
import com.tenten.outsourcing.exception.NoAuthorizedException;
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
import org.springframework.http.HttpStatus;
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

        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute(LoginStatus.LOGIN_USER) == null) {
                throw new NoAuthorizedException(ErrorCode.NO_SESSION);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestUrl) {
        return PatternMatchUtils.simpleMatch(WHITELIST, requestUrl);
    }
}
