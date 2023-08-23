package com.philips.shipment.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.philips.shipment.enums.Status;
import com.philips.shipment.models.Shipment;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ShipmentRepositoryTest {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    public void testCreateRecord() {

        // ARRANGE
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setOrigin("Mumbai");
        shipment.setDestination("Bangalore");
        shipment.setShipmentDate(LocalDateTime.of(2023, 8, 28, 0, 0));
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());

        // ACT
        Shipment savedShipment = shipmentRepository.save(shipment);

        // ASSERT
        assertNotNull(savedShipment);
        assertThat(savedShipment.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAllRecords() {

        // ARRANGE
        Shipment firstShipment = new Shipment();
        firstShipment.setTrackingNumber(UUID.randomUUID().toString());
        firstShipment.setOrigin("Mumbai");
        firstShipment.setDestination("Bangalore");
        firstShipment.setShipmentDate(LocalDateTime.of(2023, 8, 28, 0, 0));
        firstShipment.setStatus(Status.CREATED);
        firstShipment.setLastUpdated(LocalDateTime.now());

        Shipment secondShipment = new Shipment();
        secondShipment.setTrackingNumber(UUID.randomUUID().toString());
        secondShipment.setOrigin("Jaipur");
        secondShipment.setDestination("Mumbai");
        secondShipment.setShipmentDate(LocalDateTime.of(2023, 9, 4, 0, 0));
        secondShipment.setStatus(Status.CREATED);
        secondShipment.setLastUpdated(LocalDateTime.now());

        // ACT
        shipmentRepository.save(firstShipment);
        shipmentRepository.save(secondShipment);
        List<Shipment> shipments = shipmentRepository.findAll();

        // ASSERT
        assertNotNull(shipments);
        assertThat(shipments.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testGetRecordById() {

        // ARRANGE
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setOrigin("Mumbai");
        shipment.setDestination("Bangalore");
        shipment.setShipmentDate(LocalDateTime.of(2023, 8, 28, 0, 0));
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());

        Shipment savedShipment = shipmentRepository.save(shipment);

        // ACT
        Shipment getShipment = shipmentRepository.findById(savedShipment.getId()).get();

        // ASSERT
        assertNotNull(getShipment);
        assertEquals(getShipment.getId(), shipment.getId());
    }

    @Test
    public void testGetRecordByTrackingNumber() {

        // ARRANGE
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setOrigin("Mumbai");
        shipment.setDestination("Bangalore");
        shipment.setShipmentDate(LocalDateTime.of(2023, 8, 28, 0, 0));
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());

        Shipment savedShipment = shipmentRepository.save(shipment);

        // ACT
        Shipment getShipment = shipmentRepository.findByTrackingNumber(savedShipment.getTrackingNumber());

        // ASSERT
        assertNotNull(getShipment);
        assertEquals(getShipment.getTrackingNumber(), shipment.getTrackingNumber());
    }

    @Test
    public void testUpdateRecord() {

        // ARRANGE
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setOrigin("Mumbai");
        shipment.setDestination("Bangalore");
        shipment.setShipmentDate(LocalDateTime.of(2023, 8, 28, 0, 0));
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());

        Shipment savedShipment = shipmentRepository.save(shipment);

        // ACT
        Shipment updatedShipment = shipmentRepository.findByTrackingNumber(savedShipment.getTrackingNumber());
        updatedShipment.setStatus(Status.IN_TRANSIT);

        // ASSERT
        assertNotNull(updatedShipment);
        assertEquals(updatedShipment.getStatus(), Status.IN_TRANSIT);
    }

    @Test
    public void testDeleteRecord() {

        // ARRANGE
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setOrigin("Mumbai");
        shipment.setDestination("Bangalore");
        shipment.setShipmentDate(LocalDateTime.of(2023, 8, 28, 0, 0));
        shipment.setStatus(Status.CREATED);
        shipment.setLastUpdated(LocalDateTime.now());

        shipmentRepository.save(shipment);

        // ACT
        shipmentRepository.deleteById(shipment.getId());

        // ASSERT
        assertThat(shipmentRepository.findById(shipment.getId())).isEmpty();
    }
}
