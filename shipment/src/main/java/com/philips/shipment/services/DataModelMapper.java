package com.philips.shipment.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.philips.shipment.enums.Status;
import com.philips.shipment.factories.DataModelFactory;
import com.philips.shipment.models.Shipment;

@Component
public class DataModelMapper implements DataModelFactory {

    // Convert to Shipment
    public Shipment shipmentFactory(String origin, String destination, LocalDateTime shipmentDate) {
        Shipment shipment = new Shipment();
        shipment.setOrigin(origin);
        shipment.setDestination(destination);
        shipment.setShipmentDate(shipmentDate);
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());
        return shipment;
    }
}
