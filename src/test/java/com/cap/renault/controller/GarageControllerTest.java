package com.cap.renault.controller;

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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GarageController.class)
class GarageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageService garageService;

    @Autowired
    private ObjectMapper objectMapper;

    private Garage garage;
    private GarageDto dto;

    @BeforeEach
    void setUp() {
        garage = new Garage();
        garage.setId(1L);
        garage.setName("Garage Renault Casablanca");
        dto = new GarageDto();
        dto.setName("Garage Renault Casablanca");
    }

    @Test
    void testCreateGarage_Success() throws Exception {
        Mockito.when(garageService.createGarage(any(GarageDto.class)))
                .thenReturn(garage);

        mockMvc.perform(post("/api/garages/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Garage Renault Casablanca"));
    }

    @Test
    void testCreateGarage_Failure_BadRequest() throws Exception {
        GarageDto invalidBodyRequest =null;

        mockMvc.perform(post("/api/garages/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBodyRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateGarage_Success() throws Exception {
        Mockito.when(garageService.updateGarage(eq(1L), any(GarageDto.class)))
                .thenReturn(garage);

        mockMvc.perform(put("/api/garages/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Garage Renault Casablanca"));
    }

    @Test
    void testUpdateGarage_NotFound() throws Exception {
        Mockito.when(garageService.updateGarage(eq(99L), any(GarageDto.class)))
                .thenThrow(new ResourceNotFoundException("Garage not found"));
        mockMvc.perform(put("/api/garages/update/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetGarage_Success() throws Exception {
        Mockito.when(garageService.getGarage(1L))
                .thenReturn(garage);
        mockMvc.perform(get("/api/garages/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Garage Renault Casablanca"));
    }

    @Test
    void testGetGarage_NotFound() throws Exception {
        Mockito.when(garageService.getGarage(99L))
                .thenThrow(new ResourceNotFoundException("Garage not found"));
        mockMvc.perform(get("/api/garages/get/99"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testListGarages_Success() throws Exception {
        Page<Garage> page = new PageImpl<>(Collections.singletonList(garage));
        Mockito.when(garageService.listGarages(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get("/api/garages/list?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Garage Renault Casablanca"));
    }

    @Test
    void testDeleteGarage_Success() throws Exception {
        mockMvc.perform(delete("/api/garages/delete/1"))
                .andExpect(status().isOk());
        Mockito.verify(garageService).deleteGarage(1L);
    }



}