package com.philips.shipment.factories;

import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;
import com.philips.shipment.models.Shipment;

public interface DataContractFactory {

    ShipmentContract shipmentContractFactory(Shipment shipment);

    MessageStatusContract messageStatusContractFactory(Shipment shipment, String message);
}
