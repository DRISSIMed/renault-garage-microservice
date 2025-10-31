package com.cap.renault.entity;

import com.cap.renault.util.OpeningTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "garages")
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningTimeEntry> horairesOuvertureList = new ArrayList<>();

    @Transient
    private Map<DayOfWeek, List<OpeningTime>> horairesOuverture = new HashMap<>();


    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicule> vehicles = new ArrayList<>();

    //After loading entity build HashMap for data recieved from db
    @PostLoad
    private void loadMap() {
        horairesOuverture = horairesOuvertureList.stream()
                .collect(Collectors.groupingBy(
                        OpeningTimeEntry::getDayOfWeek,
                        Collectors.mapping(OpeningTimeEntry::getOpeningTime, Collectors.toList())
                ));
    }
    // Before persisting build OpeningTimeEntry
    @PrePersist
    @PreUpdate
    private void loadList() {
        horairesOuvertureList.clear();
        horairesOuverture.forEach((day, times) ->
                times.forEach(time -> {
                    OpeningTimeEntry entry = new OpeningTimeEntry();
                    entry.setDayOfWeek(day);
                    entry.setOpeningTime(time);
                    entry.setGarage(this);
                    horairesOuvertureList.add(entry);
                })
        );
    }


}
