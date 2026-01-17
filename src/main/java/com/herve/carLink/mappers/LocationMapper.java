package com.herve.carLink.mappers;

import com.herve.carLink.common.LocationPriceCalculator;
import com.herve.carLink.dtos.InvoiceResponse;
import com.herve.carLink.dtos.LocationRequest;
import com.herve.carLink.dtos.LocationResponse;
import com.herve.carLink.models.Location;
import com.herve.carLink.models.Status;
import com.herve.carLink.models.User;
import com.herve.carLink.models.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(LocationRequest locationRequest, User client, Vehicle vehicle) {
        Location location = new Location();
        location.setVehicle(vehicle);
        location.setClient(client);
        location.setStartDate(locationRequest.getStartDate());
        location.setEndDate(locationRequest.getEndDate());
        location.setStatus(Status.IN_PROGRESS);

        double totalPrice = LocationPriceCalculator.calculateTotalPrice(
                locationRequest.getStartDate(),
                locationRequest.getEndDate(),
                vehicle.getPriceRentPerDay()
        );
        location.setPrice(totalPrice);

        return location;
    }

    public LocationResponse toDto(Location location) {
        LocationResponse response = new LocationResponse();
        response.setLocationId(location.getLocationId());
        response.setVehicleId(location.getVehicle().getVehicleId());
        response.setClientId(location.getClient().getUserId());
        response.setStartDate(location.getStartDate());
        response.setEndDate(location.getEndDate());
        response.setPrice(location.getPrice());
        response.setStatus(location.getStatus().name());

        // Ajouter la facture si elle existe
        if (location.getInvoice() != null) {
            InvoiceResponse invoiceResponse = new InvoiceResponse();
            invoiceResponse.setInvoiceId(location.getInvoice().getInvoiceId());
            invoiceResponse.setDateEmission(location.getInvoice().getDateEmission());
            invoiceResponse.setAmount(location.getInvoice().getAmount());
            invoiceResponse.setStatusInvoice(location.getInvoice().getStatusInvoice().name());
            response.setInvoice(invoiceResponse);

        }
        return response;
    }
}
