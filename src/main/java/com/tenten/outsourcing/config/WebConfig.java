package com.tenten.outsourcing.config;

import com.tenten.outsourcing.filter.AuthFilter;
import com.tenten.outsourcing.filter.LoginFilter;
import com.tenten.outsourcing.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final LoginService loginService;

  @Bean
  public FilterRegistrationBean loginFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new LoginFilter() {});
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setOrder(2);
    return filterRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean authFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new AuthFilter(loginService));
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setOrder(3);
    return filterRegistrationBean;
  }
}
