package com.philips.shipment.factories;

import java.time.LocalDateTime;

import com.philips.shipment.models.Shipment;

public interface DataModelFactory {

    Shipment shipmentFactory(String origin, String destination, LocalDateTime shipmentDate);
}
