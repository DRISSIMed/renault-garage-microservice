package com.cap.renault.controller;

import com.cap.renault.dto.VehiculeDto;
import com.cap.renault.entity.Vehicule;
import com.cap.renault.exception.CostumException;
import com.cap.renault.kafka.VehiculeEventProducer;
import com.cap.renault.repository.GarageRepository;
import com.cap.renault.repository.VehiculeRepository;
import com.cap.renault.service.VehiculeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.cap.renault.dto.GarageDto;
import com.cap.renault.entity.Garage;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.service.GarageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

class VehiculeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private VehiculeService vehiculeService;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private VehiculeRepository vehiculeRepository;
    @Mock
    private VehiculeEventProducer vehiculeEventProducer;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehicule vehicule;
    private VehiculeDto dto;
    private Garage garage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(vehiculeService, "quota", 50L);
        garage = new Garage();
        garage.setId(1L);
        garage.setName("Garage Renault Casa");

        vehicule = new Vehicule();
        vehicule.setId(1L);
        vehicule.setModel("Clio 5");
        vehicule.setAnneeFabrication(2024);

        dto = new VehiculeDto();
        dto.setModel("Clio 5");
        dto.setAnneeFabrication(2024);
    }

    @Test
    void testCreateVehicule_Success() throws Exception {
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(vehiculeRepository.countByGarageId(1L)).thenReturn(5L);
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(vehicule);
        Vehicule result = vehiculeService.createVehicle(1L, dto);
        assertNotNull(result);
        assertEquals("Clio 5", result.getModel());
    }


    @Test
    void testCreateVehicule_Failure_Quota() throws Exception {
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(vehiculeRepository.countByGarageId(1L)).thenReturn(51L);
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(vehicule);
        CostumException thrown = assertThrows(
                CostumException.class,
                () -> vehiculeService.createVehicle(garage.getId(), dto),
                "Garage vehicle quota reached"
        );
        assertEquals("Garage vehicle quota reached",thrown.getMessage());
    }




}