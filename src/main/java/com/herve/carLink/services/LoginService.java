package com.herve.carLink.services;

import com.herve.carLink.dtos.LoginRequest;
import com.herve.carLink.dtos.LoginResponse;
import com.herve.carLink.models.RevokedToken;
import com.herve.carLink.models.Role;
import com.herve.carLink.repositories.RevokedTokenRepo;
import com.herve.carLink.repositories.UserRepo;
import com.herve.carLink.securities.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RevokedTokenRepo revokedTokenRepo;


    public LoginResponse login(LoginRequest loginRequest) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = userRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User not found!"));

            var accessToken = jwtService.generateAccessToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            revokedTokenRepo.save(new RevokedToken(user,refreshToken));

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(accessToken);
            loginResponse.setRefreshToken(refreshToken);
            loginResponse.setAccessTokenExpiration(JwtService.ACCESS_TOKEN_EXPIRATION);
            loginResponse.setRoles(
                    user.getRole().stream()
                            .map(Role::name)
                            .collect(Collectors.toList())
            );

            return loginResponse;
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Email or password incorrect", e);
        } catch (DisabledException e) {
            throw new RuntimeException("Account unactivated", e);
        } catch (Exception e) {
            throw new RuntimeException("Error authentication: " + e.getMessage(), e);
        }
    }
}
