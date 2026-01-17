package com.herve.carLink.services;

import com.herve.carLink.common.LocationPriceCalculator;
import com.herve.carLink.dtos.LocationRequest;
import com.herve.carLink.dtos.LocationResponse;
import com.herve.carLink.dtos.LocationUpdateRequest;
import com.herve.carLink.mappers.LocationMapper;
import com.herve.carLink.models.*;
import com.herve.carLink.repositories.InvoiceRepo;
import com.herve.carLink.repositories.LocationRepo;
import com.herve.carLink.repositories.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepo locationRepo;
    private final LocationMapper locationMapper;
    private final VehicleRepo vehicleRepo;
    private final InvoiceRepo invoiceRepo;

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

    @Transactional
    //annulation d'une
    public LocationResponse cancelLocation(Integer locationId, User client) {
        Location location = locationRepo.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        if (!location.getClient().getUserId().equals(client.getUserId())) {
            throw new RuntimeException("You are not authorized to cancel this location");
        }
        location.setStatus(Status.CANCELLED);
        location = locationRepo.save(location);
        Vehicle vehicle = location.getVehicle();
        vehicle.setAvailable(true);
        vehicleRepo.save(vehicle);
        return locationMapper.toDto(location);
    }

    @Transactional
    //pour la mise a jour
    public LocationResponse updateLocation(Integer locationId, LocationUpdateRequest locationUpdateRequest, User client) {
        Location location = locationRepo.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        if (!location.getClient().getUserId().equals(client.getUserId())) {
            throw new RuntimeException("You are not authorized to update this location");
        }
        Vehicle vehicle = location.getVehicle();
        if (!vehicle.isAvailable()) {
            throw new RuntimeException("Vehicle is not available");
        }
        location.setStartDate(locationUpdateRequest.getStartDate());
        location.setEndDate(locationUpdateRequest.getEndDate());
        double totalPrice = LocationPriceCalculator.calculateTotalPrice(
                locationUpdateRequest.getStartDate(),
                locationUpdateRequest.getEndDate(),
                vehicle.getPriceRentPerDay()
        );
        location.setPrice(totalPrice);
        location = locationRepo.save(location);
        return locationMapper.toDto(location);
    }

    @Transactional(readOnly = true)
    //verifier que la location appartient a un client
    public LocationResponse getLocationDetails(Integer locationId, User client) {
        Location location = locationRepo.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        if (!location.getClient().getUserId().equals(client.getUserId())) {
            throw new RuntimeException("You are not authorized to view this location");
        }
        return locationMapper.toDto(location);
    }

    @Transactional(readOnly = true)
    //filtrer les locations pour un vehicule grace a son id
    public List<LocationResponse> getLocationsByVehicle(Integer vehicleId) {
        List<Location> locations = locationRepo.findByVehicle_VehicleId(vehicleId);
        return locations.stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LocationResponse confirmLocation(Integer locationId) {


        Location location = locationRepo.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        location.setStatus(Status.COMPLETED);
        location = locationRepo.save(location);

        Invoice invoice = new Invoice();
        invoice.setDateEmission(new Date());
        invoice.setAmount(location.getPrice());
        invoice.setStatusInvoice(StatusInvoice.PENDING);
        invoice.setLocation(location);
        invoiceRepo.save(invoice);

        location.setInvoice(invoice);
        location = locationRepo.save(location);

        return locationMapper.toDto(location);
    }


}
