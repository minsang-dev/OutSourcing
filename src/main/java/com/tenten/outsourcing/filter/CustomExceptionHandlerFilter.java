package com.tenten.outsourcing.filter;

import com.tenten.outsourcing.exception.ErrorCode;
import com.tenten.outsourcing.exception.ExceptionResponseDto;
import com.tenten.outsourcing.exception.InternalServerException;
import com.tenten.outsourcing.exception.NoAuthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (NoAuthorizedException e) {
            setExceptionResponse(request, response, e.getErrorCode());
        }
    }

    private void setExceptionResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(
                new ExceptionResponseDto(
                        errorCode
                ).convertToJson()
        );
    }
}
