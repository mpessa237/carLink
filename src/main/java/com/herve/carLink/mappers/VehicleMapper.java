package com.herve.carLink.mappers;

import com.herve.carLink.dtos.VehicleRequest;
import com.herve.carLink.dtos.VehicleResponse;
import com.herve.carLink.models.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleRequest vehicleRequest);

    VehicleResponse toDto(Vehicle vehicle);

    void updateEntityFormRequest(VehicleRequest vehicleRequest,@MappingTarget Vehicle vehicle);
}
