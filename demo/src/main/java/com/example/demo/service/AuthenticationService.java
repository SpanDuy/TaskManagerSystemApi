package com.example.demo.service;

import com.example.demo.entity.auth.AuthenticationRequest;
import com.example.demo.entity.auth.AuthenticationResponse;
import com.example.demo.entity.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authentication(AuthenticationRequest request);

}
