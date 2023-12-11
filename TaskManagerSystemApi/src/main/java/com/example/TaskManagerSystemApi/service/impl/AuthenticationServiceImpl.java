package com.example.TaskManagerSystemApi.service.impl;

import com.example.TaskManagerSystemApi.entity.User;
import com.example.TaskManagerSystemApi.entity.auth.AuthenticationRequest;
import com.example.TaskManagerSystemApi.entity.auth.AuthenticationResponse;
import com.example.TaskManagerSystemApi.entity.auth.RegisterRequest;
import com.example.TaskManagerSystemApi.exception.NotFoundException;
import com.example.TaskManagerSystemApi.exception.RegistrationException;
import com.example.TaskManagerSystemApi.repository.UserRepository;
import com.example.TaskManagerSystemApi.security.JwtService;
import com.example.TaskManagerSystemApi.security.SecurityUtil;
import com.example.TaskManagerSystemApi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RegistrationException("User with this username or email already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()).build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("USER WITH USERNAME = " + request.getUsername() + " NOT FOUND"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public User getCurrentUser() {
        String username = securityUtil.getSessionUserEmail();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("USER WITH USERNAME = " + username + " NOT FOUND"));
    }
}