package com.cap.renault.controller;

import com.cap.renault.dto.AccessoireDto;
import com.cap.renault.entity.Accessoire;
import com.cap.renault.exception.ResourceNotFoundException;
import com.cap.renault.service.AccessoireService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccessoireController.class)
class AccessoireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessoireService accessoireService;

    @Autowired
    private ObjectMapper objectMapper;

    private Accessoire accessoire;
    private AccessoireDto dto;

    @BeforeEach
    void setUp() {
        accessoire = new Accessoire();
        accessoire.setId(1L);
        accessoire.setNom("GPS");

        dto = new AccessoireDto();
        dto.setNom("GPS");
    }

    @Test
    void testAddAccessoire_Success() throws Exception {
        Mockito.when(accessoireService.ajoutAccessoire( any(AccessoireDto.class)))
                .thenReturn(accessoire);

        mockMvc.perform(post("/api/accessoires/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("GPS"));
    }

    @Test
    void testUpdateAccessoire_Success() throws Exception {
        Mockito.when(accessoireService.updateAccessoire(eq(1L), any(AccessoireDto.class)))
                .thenReturn(accessoire);

        mockMvc.perform(put("/api/accessoires/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("GPS"));
    }

    @Test
    void testUpdateAccessoire_NotFound() throws Exception {
        Mockito.when(accessoireService.updateAccessoire(eq(99L), any(AccessoireDto.class)))
                .thenThrow(new ResourceNotFoundException("Accessoire not found"));

        mockMvc.perform(put("/api/accessoires/update/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListByVehicule_Success() throws Exception {
        Page<Accessoire> page = new PageImpl<>(Collections.singletonList(accessoire));
        Mockito.when(accessoireService.findAccessoiresByVehicule(eq(1L), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/accessoires/get-by-vehicule/1?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nom").value("GPS"));
    }

    @Test
    void testDeleteAccessoire_Success() throws Exception {
        mockMvc.perform(delete("/api/accessoires/delete/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(accessoireService).deleteAccessory(1L);
    }
}
