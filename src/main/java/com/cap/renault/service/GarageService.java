package com.cap.renault.service;

import com.cap.renault.dto.GarageDto;
import com.cap.renault.entity.Garage;
import com.cap.renault.entity.OpeningTimeEntry;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.repository.GarageRepository;
import com.cap.renault.repository.OpeningTimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class GarageService {
    @Autowired
    private  GarageRepository garageRepository;
    @Autowired
    private OpeningTimeEntryService openingTimeEntryService;



    @Transactional
    public Garage createGarage(GarageDto dto) {
        Garage g = new Garage();
        g.setName(dto.getName());
        g.setAddress(dto.getAddress());
        g.setTelephone(dto.getTelephone());
        g.setEmail(dto.getEmail());
        g.setHorairesOuverture(dto.getHorairesOuverture());
        Garage garageSaved =garageRepository.save(g);
        garageRepository.flush();
        openingTimeEntryService.createOpneningTime(dto.getHorairesOuverture(),garageSaved);
        return garageSaved;
    }

    @Transactional
    public Garage updateGarage(Long id, GarageDto dto) throws ResourceNotFoundException {
        Garage g = garageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Garage not found"));
        g.setName(dto.getName());
        g.setAddress(dto.getAddress());
        g.setTelephone(dto.getTelephone());
        g.setEmail(dto.getEmail());
        g.setHorairesOuverture(dto.getHorairesOuverture());
        openingTimeEntryService.updateOpeningTime(dto.getHorairesOuverture(),g);
        return garageRepository.save(g);
    }

    @Transactional
    public void deleteGarage(Long id) {
        garageRepository.deleteById(id);
    }

    public Garage getGarage(Long id) throws ResourceNotFoundException {
        return garageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Garage not found"));
    }

    public Page<Garage> listGarages(Pageable pageable) {
        return garageRepository.findAll(pageable);
    }

}
