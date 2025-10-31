package com.cap.renault.repository;

import com.cap.renault.entity.Vehicule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    long countByGarageId(Long garageId);
    Page<Vehicule> findByGarageId(Long garageId, Pageable pageable);
    List<Vehicule> findByModel(String model);
}
