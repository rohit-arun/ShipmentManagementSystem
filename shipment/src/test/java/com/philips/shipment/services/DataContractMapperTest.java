package com.philips.shipment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;
import com.philips.shipment.enums.Status;
import com.philips.shipment.models.Shipment;

@ExtendWith(MockitoExtension.class)
public class DataContractMapperTest {

    @InjectMocks
    private DataContractMapper dataContractMapper;

    @Test
    public void testShipmentContractFactory() {

        // ARRANGE
        int id = 1;
        String origin = "Mumbai";
        String destination = "Bangalore";
        LocalDateTime shipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);
        String trackingNumber = UUID.randomUUID().toString();

        Shipment shipment = new Shipment();
        shipment.setId(id);
        shipment.setTrackingNumber(trackingNumber);
        shipment.setOrigin(origin);
        shipment.setDestination(destination);
        shipment.setShipmentDate(shipmentDate);
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());

        // ACT
        ShipmentContract shipmentContract = dataContractMapper.shipmentContractFactory(shipment);

        // ASSERT
        assertEquals(shipmentContract.getTrackingNumber(), shipment.getTrackingNumber());
        assertEquals(shipmentContract.getOrigin(), shipment.getOrigin());
        assertEquals(shipmentContract.getDestination(), shipment.getDestination());
        assertEquals(shipmentContract.getShipmentDate(), shipment.getShipmentDate());
        assertEquals(shipmentContract.getStatus(), shipment.getStatus());
        assertEquals(shipmentContract.getLastUpdated(), shipment.getLastUpdated());
    }

    @Test
    public void testMessageStatusContractFactory() {

        // ARRANGE
        int id = 1;
        String origin = "Mumbai";
        String destination = "Bangalore";
        LocalDateTime shipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);
        String trackingNumber = UUID.randomUUID().toString();

        Shipment shipment = new Shipment();
        shipment.setId(id);
        shipment.setTrackingNumber(trackingNumber);
        shipment.setOrigin(origin);
        shipment.setDestination(destination);
        shipment.setShipmentDate(shipmentDate);
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());

        String message = "The shipment has been created";

        // ACT
        MessageStatusContract messageStatusContract = dataContractMapper.messageStatusContractFactory(shipment,
                message);

        // ASSERT
        assertEquals(messageStatusContract.getTrackingNumber(), shipment.getTrackingNumber());
        assertEquals(messageStatusContract.getStatus(), shipment.getStatus());
        assertEquals(messageStatusContract.getMessage(), message);
    }
}
