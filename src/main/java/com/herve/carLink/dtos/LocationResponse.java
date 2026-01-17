package com.herve.carLink.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Integer locationId;
    private Integer vehicleId;
    private Integer clientId;
    private Date startDate;
    private Date endDate;
    private Double price;
    private String status;
    private InvoiceResponse invoice;
}
