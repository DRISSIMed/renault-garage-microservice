package com.cap.renault.service;

import com.cap.renault.dto.AccessoireDto;
import com.cap.renault.dto.VehiculeDto;
import com.cap.renault.entity.Accessoire;
import com.cap.renault.entity.Vehicule;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.repository.AccessoireRepository;
import com.cap.renault.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccessoireService {
    @Autowired
    private AccessoireRepository accessoireRepository;
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Transactional
    public Accessoire ajoutAccessoire(Long vehiculeId, AccessoireDto dto) throws ResourceNotFoundException {
        Vehicule v = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Accessoire a = new Accessoire();
        a.setNom(dto.getNom());
        a.setDescription(dto.getDescription());
        a.setPrix(dto.getPrix());
        a.setType(dto.getType());
        a.setVehicule(v);
        return accessoireRepository.save(a);
    }

    @Transactional
    public Accessoire updateAccessoire(Long id, AccessoireDto dto) throws ResourceNotFoundException {
        Accessoire a = accessoireRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accessoire not found"));
        a.setNom(dto.getNom());
        a.setDescription(dto.getDescription());
        a.setPrix(dto.getPrix());
        a.setType(dto.getType());
        return accessoireRepository.save(a);
    }

    @Transactional
    public void deleteAccessory(Long id) {
        accessoireRepository.deleteById(id);
    }

    public Page<Accessoire> findAccessoiresByVehicule(Long vehiculeId, Pageable pageable) {
        return accessoireRepository.findByVehiculeId(vehiculeId, pageable);
    }

}
