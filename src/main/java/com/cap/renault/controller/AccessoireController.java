package com.cap.renault.controller;

import com.cap.renault.dto.AccessoireDto;
import com.cap.renault.dto.VehiculeDto;
import com.cap.renault.entity.Accessoire;
import com.cap.renault.entity.Vehicule;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.service.AccessoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accessoires")
public class AccessoireController {
    @Autowired
    private AccessoireService accessoireService;

    @PostMapping("/create/{vehicleId}")
    public ResponseEntity<Accessoire> addAccessoire(@PathVariable Long vehicleId, @Validated @RequestBody AccessoireDto dto) throws ResourceNotFoundException {
        Accessoire created = accessoireService.ajoutAccessoire(vehicleId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Accessoire> update(@PathVariable Long id, @Validated @RequestBody AccessoireDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(accessoireService.updateAccessoire(id, dto));
    }

    @GetMapping("/get-by-vehicule/{vehiculeId}")
    public ResponseEntity<Page<Accessoire>> listByVehicule(
            @PathVariable Long vehiculeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable p = PageRequest.of(page, size);
        return ResponseEntity.ok(accessoireService.findAccessoiresByVehicule(vehiculeId, p));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accessoireService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }
}
