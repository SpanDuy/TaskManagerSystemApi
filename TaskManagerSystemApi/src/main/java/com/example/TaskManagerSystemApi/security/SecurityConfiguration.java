package com.example.TaskManagerSystemApi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    private final static String[] ALLOWED_URL_FOR_ALL = new String[]{
            "/auth/**",
            "/webjars/**", "/v2/api-docs/**",
            "/configuration/ui/**", "/swagger-resources/**",
            "/configuration/security/**", "/swagger-ui.html/**",
            "/swagger-ui.html#/**","/swagger-ui/**","/v3/api-docs/**",
            "/tasks"
    };

    private final static String[] ALLOWED_URL_FOR_AUTHENTICATED = new String[]{
            "tasks/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(req->{
                    req.requestMatchers(ALLOWED_URL_FOR_ALL).permitAll()
                            .requestMatchers(ALLOWED_URL_FOR_AUTHENTICATED)
                            .authenticated();
                })
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}