package com.cap.renault.service;

import com.cap.renault.dto.VehiculeDto;
import com.cap.renault.entity.Garage;
import com.cap.renault.entity.Vehicule;
import com.cap.renault.exception.CostumException;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.kafka.VehiculeEventProducer;
import com.cap.renault.repository.GarageRepository;
import com.cap.renault.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculeService {
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehiculeEventProducer vehiculeEventProducer;
    @Value("${app.garage.vehicule-quota}")
    private long quota;

    @Transactional
    public Vehicule createVehicle(Long garageId, VehiculeDto dto) throws CostumException, ResourceNotFoundException {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new ResourceNotFoundException("Garage not found"));
        long vehiculeExistInGarage = vehiculeRepository.countByGarageId(garageId);
        if (vehiculeExistInGarage >= quota) throw new CostumException("Garage vehicle quota reached");

        Vehicule v = new Vehicule();
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setAnneeFabrication(dto.getAnneeFabrication());
        v.setTypeCarburant(dto.getTypeCarburant());
        v.setGarage(garage);

        Vehicule saved = vehiculeRepository.save(v);
        vehiculeEventProducer.publishCreatedVehicule(saved);
        return saved;
    }


    public Page<Vehicule> findVehiculesByGarage(Long garageId, Pageable pageable) {
        return vehiculeRepository.findByGarageId(garageId, pageable);
    }

    public List<Vehicule> findByModel(String model) {
        return vehiculeRepository.findByModel(model);
    }

    @Transactional
    public Vehicule updateVehicule(Long id, VehiculeDto dto) throws ResourceNotFoundException {
        Vehicule v = vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setAnneeFabrication(dto.getAnneeFabrication());
        v.setTypeCarburant(dto.getTypeCarburant());
        return vehiculeRepository.save(v);
    }

    @Transactional
    public void deleteVehicule(Long id) {
        vehiculeRepository.deleteById(id);
    }
}
