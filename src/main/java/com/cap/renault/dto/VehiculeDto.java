package com.cap.renault.dto;

import com.cap.renault.util.OpeningTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeDto {
    private Long id;
    private String model;
    private String brand;
    private Integer anneeFabrication;
    private String typeCarburant;
}
