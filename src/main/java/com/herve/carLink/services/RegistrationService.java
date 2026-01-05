package com.herve.carLink.services;

import com.herve.carLink.dtos.UserRequest;
import com.herve.carLink.models.Role;
import com.herve.carLink.models.User;
import com.herve.carLink.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRequest userRequest) {

        if (userRepo.findByEmail(userRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("email already exists!!");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setDrivingLicense(userRequest.getDrivingLicense());
        user.setRole(Set.of(Role.USER));
        user.setEnabled(true);
        user.setAccountLocked(false);

        userRepo.save(user);
    }
}
