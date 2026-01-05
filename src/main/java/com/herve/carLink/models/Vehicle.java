package com.herve.carLink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;
    private String mark;
    private String model;
    private int year;
    private String type;
    private boolean available;
    private Double priceRentPerDay;


    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency ;
}
