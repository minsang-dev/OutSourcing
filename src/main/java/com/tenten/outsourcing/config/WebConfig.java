package com.tenten.outsourcing.config;

import com.tenten.outsourcing.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public FilterRegistrationBean loginFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new LoginFilter() {});
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setOrder(2);
    return filterRegistrationBean;
  }

}
