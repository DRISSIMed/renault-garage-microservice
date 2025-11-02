package com.cap.renault.service;

import com.cap.renault.dto.VehiculeDto;
import com.cap.renault.entity.Accessoire;
import com.cap.renault.entity.Garage;
import com.cap.renault.entity.Vehicule;
import com.cap.renault.exception.CostumException;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.kafka.VehiculeEventProducer;
import com.cap.renault.repository.AccessoireRepository;
import com.cap.renault.repository.GarageRepository;
import com.cap.renault.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VehiculeService {
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private AccessoireRepository accessoireRepository;

    @Autowired
    private VehiculeEventProducer vehiculeEventProducer;
    @Value("${app.garage.vehicule-quota}")
    private long quota;

    @Transactional
    public Vehicule createVehicle( VehiculeDto dto) throws CostumException, ResourceNotFoundException {
        Set<Garage> garages =new HashSet<>();
        Set<Accessoire> accessoires= new HashSet<>();
        for (Long id : dto.getGaragesIds()) {
            Garage garage= garageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Garage not found"));
            long vehiculeExistInGarage = vehiculeRepository.countByGarageVehicules_Id(id);
            if (vehiculeExistInGarage >= quota) throw new CostumException("Garage vehicle quota reached");
            garages.add(garage);
        }

        for (Long id : dto.getAccessoiresIds()) {
           Optional<Accessoire> accessoire= Optional.ofNullable(accessoireRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accessoire not found")));
             if(accessoire.isPresent()){
                 accessoires.add(accessoire.get());
             }

        }

        Vehicule v = new Vehicule();
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setAnneeFabrication(dto.getAnneeFabrication());
        v.setTypeCarburant(dto.getTypeCarburant());
        v.setGarageVehicules(garages);
        v.setAccessoiresVehicules(accessoires);

        Vehicule saved = vehiculeRepository.save(v);
        vehiculeEventProducer.publishCreatedVehicule(saved);
        return saved;
    }


    public Page<Vehicule> findVehiculesByGarage(Long garageId, Pageable pageable) {
        return vehiculeRepository.findByGarageVehicules_Id(garageId, pageable);
    }

    public List<Vehicule> findByModel(String model) {
        return vehiculeRepository.findByModel(model);
    }

    @Transactional
    public Vehicule updateVehicule(Long id, VehiculeDto dto) throws ResourceNotFoundException, CostumException {
        Vehicule v = vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Set<Garage> garages =new HashSet<>();
        Set<Accessoire> accessoires= new HashSet<>();
        for (Long idGarage : dto.getGaragesIds()) {
            Garage garage= garageRepository.findById(idGarage).orElseThrow(() -> new ResourceNotFoundException("Garage not found"));
            long vehiculeExistInGarage = vehiculeRepository.countByGarageVehicules_Id(idGarage);
            if (vehiculeExistInGarage >= quota) throw new CostumException("Garage vehicle quota reached");
            garages.add(garage);
        }

        for (Long idAccessoire : dto.getAccessoiresIds()) {
            Optional<Accessoire> accessoire= Optional.ofNullable(accessoireRepository.findById(idAccessoire).orElseThrow(() -> new ResourceNotFoundException("Accessoire not found")));
            if(accessoire.isPresent()){
                accessoires.add(accessoire.get());
            }

        }
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setAnneeFabrication(dto.getAnneeFabrication());
        v.setTypeCarburant(dto.getTypeCarburant());
        v.setGarageVehicules(garages);
        v.setAccessoiresVehicules(accessoires);
        return vehiculeRepository.save(v);
    }

    @Transactional
    public void deleteVehicule(Long id) {
        vehiculeRepository.deleteById(id);
    }
}
