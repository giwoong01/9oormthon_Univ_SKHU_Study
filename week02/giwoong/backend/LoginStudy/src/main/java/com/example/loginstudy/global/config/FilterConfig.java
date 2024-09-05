package com.example.loginstudy.global.config;

import com.example.loginstudy.global.jwt.JwtAuthorizationFilter;
import com.example.loginstudy.global.jwt.TokenProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final TokenProvider tokenProvider;

    public FilterConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtFilterRegistrationBean() {
        FilterRegistrationBean<JwtAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthorizationFilter(tokenProvider));
        registrationBean.addUrlPatterns(
                "/api/members/*"
        );
        return registrationBean;
    }
}
