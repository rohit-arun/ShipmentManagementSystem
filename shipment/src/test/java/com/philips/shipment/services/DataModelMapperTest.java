package com.philips.shipment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.philips.shipment.enums.Status;
import com.philips.shipment.models.Shipment;

@ExtendWith(MockitoExtension.class)
public class DataModelMapperTest {

    @InjectMocks
    private DataModelMapper dataModelMapper;

    @Test
    public void testShipmentFactory() {

        // ARRANGE
        String origin = "Mumbai";
        String destination = "Bangalore";
        LocalDateTime shipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);

        Shipment expectedShipment = new Shipment();
        expectedShipment.setOrigin(origin);
        expectedShipment.setDestination(destination);
        expectedShipment.setShipmentDate(shipmentDate);
        expectedShipment.setStatus(Status.CREATED);

        // ACT
        Shipment receivedShipment = dataModelMapper.shipmentFactory(origin, destination, shipmentDate);

        // ASSERT
        assertEquals(receivedShipment.getOrigin(), expectedShipment.getOrigin());
        assertEquals(receivedShipment.getDestination(), expectedShipment.getDestination());
        assertEquals(receivedShipment.getShipmentDate(), expectedShipment.getShipmentDate());
        assertEquals(receivedShipment.getStatus(), expectedShipment.getStatus());
    }
}
