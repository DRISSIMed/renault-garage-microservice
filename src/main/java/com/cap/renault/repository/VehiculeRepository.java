package com.cap.renault.repository;

import com.cap.renault.entity.Vehicule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    @Query("SELECT COUNT(v) FROM Vehicule v JOIN v.garageVehicules g WHERE g.id = :garageId")
    long countByGarageVehicules_Id(@Param("garageId")Long garageId);
    @Query("SELECT v FROM Vehicule v JOIN v.garageVehicules g WHERE g.id = :garageId")
    Page<Vehicule> findByGarageVehicules_Id(@Param("garageId")Long garageId, Pageable pageable);

    List<Vehicule> findByModel(String model);
}
