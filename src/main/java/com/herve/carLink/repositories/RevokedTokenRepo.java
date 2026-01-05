package com.herve.carLink.repositories;

import com.herve.carLink.models.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevokedTokenRepo extends JpaRepository<RevokedToken,Integer> {

    Optional<RevokedToken> findByToken(String token);
}
