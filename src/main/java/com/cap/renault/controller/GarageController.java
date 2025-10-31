package com.cap.renault.controller;

import com.cap.renault.dto.GarageDto;
import com.cap.renault.entity.Garage;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/garages")
public class GarageController {
    @Autowired
    private GarageService garageService;

    @PostMapping("/create")
    public ResponseEntity<Garage> create(@Validated @RequestBody GarageDto dto) {
        Garage created = garageService.createGarage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Garage> update(@PathVariable Long id, @Validated @RequestBody GarageDto dto) throws ResourceNotFoundException {
        Garage updated = garageService.updateGarage(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Garage> get(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(garageService.getGarage(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        garageService.deleteGarage(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Garage>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String orderBy
    ) {
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(garageService.listGarages(p));
    }


}
