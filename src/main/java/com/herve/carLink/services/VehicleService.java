package com.herve.carLink.services;

import com.herve.carLink.dtos.VehicleRequest;
import com.herve.carLink.dtos.VehicleResponse;
import com.herve.carLink.mappers.VehicleMapper;
import com.herve.carLink.models.Vehicle;
import com.herve.carLink.repositories.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
