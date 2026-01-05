package com.herve.carLink.services;

import com.herve.carLink.dtos.LoginRequest;
import com.herve.carLink.dtos.LoginResponse;
import com.herve.carLink.repositories.UserRepo;
import com.herve.carLink.securities.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new IllegalArgumentException("user not found!!"));


        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);

        return loginResponse;


    }
}
