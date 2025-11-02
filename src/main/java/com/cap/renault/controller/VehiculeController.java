package com.cap.renault.controller;

import com.cap.renault.dto.VehiculeDto;
import com.cap.renault.entity.Vehicule;
import com.cap.renault.exception.CostumException;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {
    @Autowired
    private VehiculeService vehiculeService;
    @PostMapping("/create")
    public ResponseEntity<Vehicule> create(@Validated @RequestBody VehiculeDto dto) throws CostumException, ResourceNotFoundException {
        Vehicule v = vehiculeService.createVehicle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(v);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vehicule> update(@PathVariable Long id, @Validated @RequestBody VehiculeDto dto) throws ResourceNotFoundException, CostumException {
        return ResponseEntity.ok(vehiculeService.updateVehicule(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehiculeService.deleteVehicule(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/get-by-garage/{garageId}")
    public ResponseEntity<Page<Vehicule>> listByGarage(
            @PathVariable Long garageId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable p = PageRequest.of(page, size);
        return ResponseEntity.ok(vehiculeService.findVehiculesByGarage(garageId, p));
    }

    @GetMapping("/by-model")
    public ResponseEntity<List<Vehicule>> findByModel(@RequestParam(required = false) String model) {
        return ResponseEntity.ok(vehiculeService.findByModel(model));
    }
}
