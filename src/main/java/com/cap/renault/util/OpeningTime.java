package com.cap.renault.util;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OpeningTime {
    private LocalTime startTime;
    private LocalTime endTime;
}
