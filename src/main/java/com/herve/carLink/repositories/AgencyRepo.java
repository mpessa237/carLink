package com.herve.carLink.repositories;

import com.herve.carLink.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepo extends JpaRepository<Agency,Integer> {
}
