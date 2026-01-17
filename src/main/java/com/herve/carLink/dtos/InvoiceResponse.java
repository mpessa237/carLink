package com.herve.carLink.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    private Integer invoiceId;
    private Date dateEmission;
    private Double amount;
    private String statusInvoice;
}
