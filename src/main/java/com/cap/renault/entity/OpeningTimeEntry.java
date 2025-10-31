package com.cap.renault.entity;

import com.cap.renault.util.OpeningTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "opening-time")
public class OpeningTimeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Embedded
    private OpeningTime openingTime;

    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;
}
