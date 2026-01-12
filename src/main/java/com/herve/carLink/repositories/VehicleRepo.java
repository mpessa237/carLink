package com.herve.carLink.repositories;

import com.herve.carLink.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle,Integer> {
    Page<Vehicle> findByAvailableTrue(Pageable pageable);

    List<Vehicle> findByDeletedFalse();
    List<Vehicle> findByAvailableTrueAndDeletedFalse();

}
