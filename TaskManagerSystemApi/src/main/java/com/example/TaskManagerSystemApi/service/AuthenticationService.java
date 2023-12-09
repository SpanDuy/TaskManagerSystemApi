package com.example.TaskManagerSystemApi.service;

import com.example.TaskManagerSystemApi.entity.auth.AuthenticationRequest;
import com.example.TaskManagerSystemApi.entity.auth.AuthenticationResponse;
import com.example.TaskManagerSystemApi.entity.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authentication(AuthenticationRequest request);

}
