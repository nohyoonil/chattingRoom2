package com.example.chattingroom2.oauth.handler;

import com.example.chattingroom2.oauth.model.UserPrincipal;
import com.example.chattingroom2.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getName(), Duration.ofDays(1));
        Cookie cookie = new Cookie("Authorization", accessToken);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        String targetUri = UriComponentsBuilder.fromUriString("http://localhost:8080/success")
                .queryParam("socialID", user.getName())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUri);
    }
}
