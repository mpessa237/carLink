package com.herve.carLink.controllers;

import com.herve.carLink.dtos.VehicleRequest;
import com.herve.carLink.dtos.VehicleResponse;
import com.herve.carLink.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VehicleResponse> save(@Validated @RequestBody VehicleRequest vehicleRequest){
        VehicleResponse response = vehicleService.addVehicle(vehicleRequest);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{vehicleId}/unavailable")
    public ResponseEntity<VehicleResponse> markVehicleAsUnavailable(@PathVariable Integer vehicleId){
        VehicleResponse response = vehicleService.markVehicleAsUnavailable(vehicleId);
       return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<VehicleResponse>> getAvailableVehicles(Pageable pageable) {
        List<VehicleResponse> responses = vehicleService.getAvailableVehicles(pageable);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{vehicleId}/soft-delete")
    public ResponseEntity<VehicleResponse> softDeleteVehicle(@PathVariable Integer vehicleId) {
        VehicleResponse response = vehicleService.softDeleteVehicle(vehicleId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{vehicleId}/reactivate")
    public ResponseEntity<VehicleResponse> reactivateVehicle(@PathVariable Integer vehicleId) {
        VehicleResponse response = vehicleService.reactivateVehicle(vehicleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllNonDeletedVehicles() {
        List<VehicleResponse> responses = vehicleService.getAllNonDeletedVehicles();
        return ResponseEntity.ok(responses);
    }
}
