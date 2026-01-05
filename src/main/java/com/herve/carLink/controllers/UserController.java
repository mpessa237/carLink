package com.herve.carLink.controllers;

import com.herve.carLink.common.ApiResponse;
import com.herve.carLink.dtos.LoginRequest;
import com.herve.carLink.dtos.LoginResponse;
import com.herve.carLink.dtos.UserRequest;
import com.herve.carLink.services.LoginService;
import com.herve.carLink.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final RegistrationService registrationService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<String> save(@Validated @RequestBody UserRequest userRequest){
        registrationService.register(userRequest);
        return ResponseEntity.ok("user register successfully!!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Validated @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(loginService.authenticate(loginRequest));
    }
}
