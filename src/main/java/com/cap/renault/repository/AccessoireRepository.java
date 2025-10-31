package com.cap.renault.repository;

import com.cap.renault.entity.Accessoire;
import com.cap.renault.entity.Garage;
import com.cap.renault.entity.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessoireRepository  extends JpaRepository<Accessoire, Long> {
    Page<Accessoire> findByVehiculeId(Long vehiculeId, Pageable pageable);
}
