package com.herve.carLink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agency")
public class Agency {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer agencyId;
    private String name;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicleList = new ArrayList<>();

    //afficher les agences disponibles
    //afficher les vehicules par agence
    //gestion des agences
}
