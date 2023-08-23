package com.philips.shipment.factories;

import java.time.LocalDateTime;
import java.util.List;

import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;

public interface ShipmentServiceFactory {

    MessageStatusContract addShipment(String origin, String destination, LocalDateTime shipmentDate);

    MessageStatusContract updateShipment(String trackingNumber, String status);

    List<ShipmentContract> getAllShipments();

    ShipmentContract getShipmentByTrackingNumber(String trackingNumber);

    MessageStatusContract removeShipmentByTrackingNumber(String trackingNumber);
}
