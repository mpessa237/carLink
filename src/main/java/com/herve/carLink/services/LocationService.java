package com.herve.carLink.services;

import com.herve.carLink.dtos.LocationRequest;
import com.herve.carLink.dtos.LocationResponse;
import com.herve.carLink.mappers.LocationMapper;
import com.herve.carLink.models.Location;
import com.herve.carLink.models.User;
import com.herve.carLink.models.Vehicle;
import com.herve.carLink.repositories.LocationRepo;
import com.herve.carLink.repositories.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepo locationRepo;
    private final LocationMapper locationMapper;
    private final VehicleRepo vehicleRepo;

    @Transactional
    public LocationResponse createLocation(LocationRequest locationRequest, User client) {

        Vehicle vehicle = vehicleRepo.findById(locationRequest.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.isAvailable()) {
            throw new RuntimeException("Vehicle is not available");
        }

        Date now = new Date();
        if (locationRequest.getStartDate().before(now)) {
            throw new RuntimeException("Start date must be in the future");
        }
        if (locationRequest.getEndDate().before(locationRequest.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        Location location = locationMapper.toEntity(locationRequest, client, vehicle);

        vehicle.setAvailable(false);
        vehicleRepo.save(vehicle);

        location = locationRepo.save(location);
        return locationMapper.toDto(location);
    }
}
