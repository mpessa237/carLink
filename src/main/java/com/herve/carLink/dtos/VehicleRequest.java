package com.herve.carLink.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {
    @NotBlank(message = "Mark is mandatory")
    private String mark;
    @NotBlank(message = "Model is mandatory")
    private String model;
    @NotBlank(message = "Type is mandatory")
    private String type;
    private int year;
    private boolean available;
    private Double priceRentPerDay;
}
