package com.herve.carLink.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationUpdateRequest {
    private Date startDate;
    private Date endDate;
}
