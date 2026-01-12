package com.herve.carLink.services;

import com.herve.carLink.dtos.VehicleRequest;
import com.herve.carLink.dtos.VehicleResponse;
import com.herve.carLink.mappers.VehicleMapper;
import com.herve.carLink.models.Vehicle;
import com.herve.carLink.repositories.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepo vehicleRepo;
    private final VehicleMapper vehicleMapper;

    public VehicleResponse addVehicle(VehicleRequest vehicleRequest){
        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequest);
        vehicle = vehicleRepo.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    //marque un vehicule comme indisponible dans la BD
    @Transactional
    public VehicleResponse markVehicleAsUnavailable(Integer vehicleId){
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(()-> new RuntimeException("vehicle not found!"));
        vehicle.setAvailable(false);
        vehicle = vehicleRepo.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    //retournes une liste paginee de vehicule disponible
    public List<VehicleResponse> getAvailableVehicles(Pageable pageable){
        Page<Vehicle> vehicles = vehicleRepo.findByAvailableTrue(pageable);
        return vehicles.stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    //recupere les vehicules qui n'ont pas ete supprimer logiquement
    public List<VehicleResponse> getAllNonDeletedVehicles() {
        List<Vehicle> vehicles = vehicleRepo.findByDeletedFalse();
        return vehicles.stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public VehicleResponse softDeleteVehicle(Integer vehicleId) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle with ID " + vehicleId + " not found"));
        vehicle.setDeleted(true);
        vehicle.setDeletedAt(LocalDateTime.now());
        vehicle = vehicleRepo.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    @Transactional
    public VehicleResponse reactivateVehicle(Integer vehicleId) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle with ID " + vehicleId + " not found"));
        vehicle.setDeleted(false);
        vehicle.setDeletedAt(null);
        vehicle = vehicleRepo.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

}
