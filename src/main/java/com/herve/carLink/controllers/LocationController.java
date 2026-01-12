package com.herve.carLink.controllers;

import com.herve.carLink.dtos.LocationRequest;
import com.herve.carLink.dtos.LocationResponse;
import com.herve.carLink.models.User;
import com.herve.carLink.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(
             @RequestBody LocationRequest locationRequest,
            @AuthenticationPrincipal User client
    ){
        LocationResponse response = locationService.createLocation(locationRequest, client);
        return ResponseEntity.ok(response);
    }
}
