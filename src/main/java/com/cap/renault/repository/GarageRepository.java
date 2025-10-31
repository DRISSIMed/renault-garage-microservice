package com.cap.renault.repository;

import com.cap.renault.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository  extends JpaRepository<Garage, Long> {
}
