package com.example.loginstudy.auth.oauth2;


import com.example.loginstudy.auth.dto.CustomOAuth2User;
import com.example.loginstudy.auth.jwt.JWTUtil;
import com.example.loginstudy.user.domain.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private static final long JWT_EXPIRATION_TIME_SECONDS = 60 * 60 * 60;
    private static final String AUTHORIZATION_COOKIE_NAME = "Authorization";
    private static final int COOKIE_EXPIRATION_TIME_SECONDS = 60 * 60 * 60;
    private static final String DEFAULT_REDIRECT_URL = "/";

    @Value("${redirectUrl}")
    private String redirectUrl;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String role = extractRoleFromAuthorities(authentication.getAuthorities());

        String token = jwtUtil.createJwt(username, role, JWT_EXPIRATION_TIME_SECONDS);

        addJwtCookie(response, token);
        redirectToHomePage(response);
    }

    private String extractRoleFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        if (iterator.hasNext()) {
            return iterator.next().getAuthority();
        }
        return Role.ROLE_USER.name();
    }

    private void addJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = createCookie(token);
        response.addCookie(cookie);
    }

    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie(AUTHORIZATION_COOKIE_NAME, value);
        cookie.setMaxAge(COOKIE_EXPIRATION_TIME_SECONDS);
        cookie.setSecure(true);
        cookie.setPath(DEFAULT_REDIRECT_URL);
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void redirectToHomePage(HttpServletResponse response) throws IOException {
        response.sendRedirect(redirectUrl);
    }
}
