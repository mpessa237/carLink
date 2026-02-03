package com.herve.carLink.controllers;

import com.herve.carLink.dtos.LocationRequest;
import com.herve.carLink.dtos.LocationResponse;
import com.herve.carLink.dtos.LocationUpdateRequest;
import com.herve.carLink.models.User;
import com.herve.carLink.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("{locationId}/cancel")
    public ResponseEntity<LocationResponse> cancelLocation(
            @PathVariable Integer locationId,
            @AuthenticationPrincipal User client
    ){
        LocationResponse response = locationService.cancelLocation(locationId, client);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{locationId}")
    public ResponseEntity<LocationResponse> updateLocation(
            @PathVariable Integer locationId,
            @RequestBody LocationUpdateRequest locationUpdateRequest,
            @AuthenticationPrincipal User client) {
        LocationResponse response = locationService.updateLocation(locationId, locationUpdateRequest, client);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationResponse> getLocationDetails(
            @PathVariable Integer locationId,
            @AuthenticationPrincipal User client) {
        LocationResponse response = locationService.getLocationDetails(locationId, client);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<LocationResponse>> getLocationsByVehicle(
            @PathVariable Integer vehicleId) {
        List<LocationResponse> responses = locationService.getLocationsByVehicle(vehicleId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{locationId}/confirm")
    public ResponseEntity<LocationResponse> confirmLocation(
            @PathVariable Integer locationId,
            @AuthenticationPrincipal UserDetails userDetails ) {

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Only administrators can confirm a location");
        }

        LocationResponse response = locationService.confirmLocation(locationId);
        return ResponseEntity.ok(response);
    }


}
