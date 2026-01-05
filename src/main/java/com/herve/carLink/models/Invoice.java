package com.herve.carLink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;
    private Date dateEmission;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private StatusInvoice statusInvoice;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
