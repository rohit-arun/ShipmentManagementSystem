package com.philips.shipment.configuration;

import org.springframework.stereotype.Component;

@Component
public class ConfigureMessages {

    private String shipmentCreatedMessage = "The shipment has been created";
    private String shipmentUpdatedMessage = "The shipment has been updated";
    private String shipmentDeletedMessage = "The shipment has been deleted";

    public String getShipmentCreatedMessage() {
        return shipmentCreatedMessage;
    }

    public String getShipmentUpdatedMessage() {
        return shipmentUpdatedMessage;
    }

    public String getShipmentDeletedMessage() {
        return shipmentDeletedMessage;
    }

}
