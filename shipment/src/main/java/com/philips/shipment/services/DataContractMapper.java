package com.philips.shipment.services;

import org.springframework.stereotype.Component;

import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;
import com.philips.shipment.factories.DataContractFactory;
import com.philips.shipment.models.Shipment;

@Component
public class DataContractMapper implements DataContractFactory {

    // Convert Shipment to ShipmentContract
    public ShipmentContract shipmentContractFactory(Shipment shipment) {
        ShipmentContract shipmentContract = new ShipmentContract();
        shipmentContract.setTrackingNumber(shipment.getTrackingNumber());
        shipmentContract.setOrigin(shipment.getOrigin());
        shipmentContract.setDestination(shipment.getDestination());
        shipmentContract.setShipmentDate(shipment.getShipmentDate());
        shipmentContract.setStatus(shipment.getStatus());
        shipmentContract.setLastUpdated(shipment.getLastUpdated());
        return shipmentContract;
    }

    // Convert String to MessageStatusContract
    public MessageStatusContract messageStatusContractFactory(Shipment shipment, String message) {
        MessageStatusContract messageStatusContract = new MessageStatusContract();
        messageStatusContract.setTrackingNumber(shipment.getTrackingNumber());
        messageStatusContract.setStatus(shipment.getStatus());
        messageStatusContract.setMessage(message);
        return messageStatusContract;
    }
}
