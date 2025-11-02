package com.cap.renault.repository;

import com.cap.renault.entity.Accessoire;
import com.cap.renault.entity.Garage;
import com.cap.renault.entity.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccessoireRepository  extends JpaRepository<Accessoire, Long> {
    @Query("SELECT a FROM Accessoire a JOIN a.vehicules av WHERE av.id = :vehiculeId")
    Page<Accessoire> findByVehiculeId(@Param("vehiculeId")Long vehiculeId, Pageable pageable);
}
