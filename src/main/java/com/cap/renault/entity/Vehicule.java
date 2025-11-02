package com.cap.renault.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicules")
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehiculeId")
    private Long id;

    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer anneeFabrication;

    @Column(nullable = false)
    private String typeCarburant;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "garage_id")
//    private Garage garage;

    @ManyToMany
    @JoinTable(name = "garage_vehicules",
            joinColumns = @JoinColumn(name = "vehiculeId"),
            inverseJoinColumns = @JoinColumn(name = "garageId"))
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("garage")
    private Set<Garage> garageVehicules = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "accessoires_vehicules",
            joinColumns = @JoinColumn(name = "vehiculeId"),
            inverseJoinColumns = @JoinColumn(name = "accessoireId"))
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Accessoire> accessoiresVehicules = new HashSet<>();

//    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Accessoire> accessories = new ArrayList<>();
}
