package com.cap.renault.entity;

import com.cap.renault.util.OpeningTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "garages")
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garageId")
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
    @JsonIgnoreProperties("garage")
    private List<OpeningTimeEntry> horairesOuvertureList = new ArrayList<>();

    @Transient
    private Map<DayOfWeek, List<OpeningTime>> horairesOuverture = new HashMap<>();


    @ManyToMany(mappedBy = "garageVehicules")
    @EqualsAndHashCode.Exclude
    private Set<Vehicule> vehicules = new HashSet<>();

    //After loading entity build HashMap for data recieved from db
    @PostLoad
    private void loadMap() {
        horairesOuverture = horairesOuvertureList.stream()
                .collect(Collectors.groupingBy(
                        OpeningTimeEntry::getDayOfWeek,
                        Collectors.mapping(OpeningTimeEntry::getOpeningTime, Collectors.toList())
                ));
    }


}
