package com.herve.carLink.repositories;

import com.herve.carLink.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<Location,Integer> {
    List<Location> findByVehicle_VehicleId(Integer vehicleId);

}
