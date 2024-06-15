package com.example.chattingroom2.config;

import com.example.chattingroom2.jwt.JwtFilter;
import com.example.chattingroom2.oauth.handler.AuthenticationEntryPoint;
import com.example.chattingroom2.oauth.handler.CustomAuthenticationSuccessHandler;
import com.example.chattingroom2.service.CustomOauth2UserService;
import com.example.chattingroom2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomOauth2UserService oauth2UserService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final AuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(entryPoint))// 인증 없이 /api/** 경로 접근 시 login with oauth 2.0 페이지로 redirect 되는 문제 해결***
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest.requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).authenticated()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll())
                .headers(headersConfigurer ->
                        headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(loginConfigurer ->
                        loginConfigurer.authorizationEndpoint(authorizationEndpointConfig ->
                                        authorizationEndpointConfig.baseUri("/oauth2/authorization"))
                                .redirectionEndpoint(redirectionEndpointConfig ->
                                        redirectionEndpointConfig.baseUri("/login/oauth2/code/**"))// oauth login 성공 시 해당 uri로 redirect
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(oauth2UserService))// redirectionEndpoint("/login/oauth2/code/**")로 redirect 될 때 여기서 처리 ***
                                .successHandler(successHandler))// oauth 로그인 성공 시 핸들링
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/")) //for test
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtil);
    }
}
