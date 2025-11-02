package com.cap.renault.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accessoires")
public class Accessoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accessoireId")
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    @Column(nullable = false)
    private BigDecimal prix;

    @Column(nullable = false)
    private String type;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vehicule_id")
//    private Vehicule vehicule;
    @ManyToMany(mappedBy = "accessoiresVehicules")
    @EqualsAndHashCode.Exclude
    private Set<Vehicule> vehicules = new HashSet<>();
}
