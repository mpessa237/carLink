package com.herve.carLink.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private Integer vehicleId;
    private String mark;
    private String model;
    private String type;
    private int year;
    private boolean available;
    private Double priceRentPerDay;
}
