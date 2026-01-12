package com.herve.carLink.mappers;

import com.herve.carLink.dtos.VehicleRequest;
import com.herve.carLink.dtos.VehicleResponse;
import com.herve.carLink.models.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequest vehicleRequest){
        Vehicle vehicle = new Vehicle();
        vehicle.setMark(vehicleRequest.getMark());
        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setType(vehicleRequest.getType());
        vehicle.setYear(vehicleRequest.getYear());
        vehicle.setAvailable(vehicleRequest.isAvailable());
        vehicle.setPriceRentPerDay(vehicleRequest.getPriceRentPerDay());

        return vehicle;
    }

    public VehicleResponse toDto(Vehicle vehicle){
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setVehicleId(vehicle.getVehicleId());
        vehicleResponse.setMark(vehicle.getMark());
        vehicleResponse.setModel(vehicle.getModel());
        vehicleResponse.setType(vehicle.getType());
        vehicleResponse.setYear(vehicle.getYear());
        vehicleResponse.setPriceRentPerDay(vehicle.getPriceRentPerDay());
        vehicleResponse.setAvailable(vehicle.isAvailable());

        return vehicleResponse;


    }


}
