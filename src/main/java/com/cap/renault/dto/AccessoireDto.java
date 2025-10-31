package com.cap.renault.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessoireDto {
    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private String type;
}
