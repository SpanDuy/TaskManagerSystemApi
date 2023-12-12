package com.example.TaskManagerSystemApi.controller;

import com.example.TaskManagerSystemApi.entity.auth.AuthenticationRequest;
import com.example.TaskManagerSystemApi.entity.auth.AuthenticationResponse;
import com.example.TaskManagerSystemApi.entity.auth.RegisterRequest;
import com.example.TaskManagerSystemApi.exception.ResponseException;
import com.example.TaskManagerSystemApi.service.AuthenticationService;
import com.example.TaskManagerSystemApi.validator.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final Validator validator;

    public AuthenticationController(AuthenticationService authenticationService,
                                    Validator validator) {
        this.authenticationService = authenticationService;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        validator.validateRegister(request);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authentication(request));
    }
}
