package com.example.loginstudy.global.jwt;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtAuthorizationFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    public JwtAuthorizationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                if (!tokenProvider.validateToken(token)) {
                    sendCustomError(response, HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
                    return;
                }

            }

            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            sendCustomError(response, HttpServletResponse.SC_UNAUTHORIZED, "accessToken이 만료되었습니다.");
        } catch (Exception e) {
            sendCustomError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 필터 에러입니다.");
        }
    }

    private void sendCustomError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String errorJson = "{\"error\": \"" + message + "\"}";
        response.getWriter().write(errorJson);
        response.getWriter().flush();
    }
}