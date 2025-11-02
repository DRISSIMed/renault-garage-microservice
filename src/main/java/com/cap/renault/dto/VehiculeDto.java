package com.cap.renault.dto;

import com.cap.renault.entity.Garage;
import com.cap.renault.util.OpeningTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeDto {
    private Long id;
    private String model;
    private String brand;
    private Integer anneeFabrication;
    private String typeCarburant;
    private Set<Long> garagesIds;
    private Set<Long> accessoiresIds;
}
