package com.philips.shipment.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.philips.shipment.configuration.ConfigureMessages;
import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;
import com.philips.shipment.enums.Status;
import com.philips.shipment.models.Shipment;
import com.philips.shipment.repositories.ShipmentRepository;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private DataModelMapper dataModelMapper;

    @Mock
    private DataContractMapper dataContractMapper;

    @Mock
    private ConfigureMessages configureMessages;

    @InjectMocks
    private ShipmentService shipmentService;

    @Test
    public void testAddShipment() {

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

        doReturn(shipment).when(dataModelMapper).shipmentFactory(origin, destination, shipmentDate);
        doReturn(shipment).when(shipmentRepository).save(shipment);

        String message = "The shipment has been created";

        doReturn(message).when(configureMessages).getShipmentCreatedMessage();

        MessageStatusContract expectedMessageStatusContract = new MessageStatusContract();
        expectedMessageStatusContract.setTrackingNumber(shipment.getTrackingNumber());
        expectedMessageStatusContract.setStatus(shipment.getStatus());
        expectedMessageStatusContract.setMessage(message);

        doReturn(expectedMessageStatusContract)
                .when(dataContractMapper)
                .messageStatusContractFactory(shipment, message);

        // ACT
        MessageStatusContract receivedMessageStatusContract = shipmentService.addShipment(origin, destination,
                shipmentDate);

        // ASSERT
        assertNotNull(receivedMessageStatusContract);
        assertEquals(receivedMessageStatusContract, expectedMessageStatusContract);
    }

    @Test
    public void testUpdateShipmentWhenShipmentIsNotNull() {

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

        doReturn(shipment).when(shipmentRepository).findByTrackingNumber(trackingNumber);

        String stringStatus = "IN_TRANSIT";
        Status status = Status.valueOf(stringStatus);
        shipment.setStatus(status);
        shipment.setLastUpdated(LocalDateTime.now());

        doReturn(shipment).when(shipmentRepository).save(shipment);

        String message = "The shipment has been updated";

        doReturn(message).when(configureMessages).getShipmentUpdatedMessage();

        MessageStatusContract expectedMessageStatusContract = new MessageStatusContract();
        expectedMessageStatusContract.setTrackingNumber(shipment.getTrackingNumber());
        expectedMessageStatusContract.setStatus(shipment.getStatus());
        expectedMessageStatusContract.setMessage(message);

        doReturn(expectedMessageStatusContract)
                .when(dataContractMapper)
                .messageStatusContractFactory(shipment, message);

        // ACT
        MessageStatusContract receivedMessageStatusContract = shipmentService.updateShipment(trackingNumber,
                stringStatus);

        // ASSERT
        assertNotNull(receivedMessageStatusContract);
        assertEquals(receivedMessageStatusContract, expectedMessageStatusContract);
    }

    @Test
    public void testUpdateShipmentWhenShipmentIsNull() {

        // ARRANGE
        String trackingNumber = UUID.randomUUID().toString();
        String stringStatus = "IN_TRANSIT";
        Shipment shipment = null;

        doReturn(shipment).when(shipmentRepository).findByTrackingNumber(trackingNumber);

        // ACT
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.ACCEPTED);
        try {
            shipmentService.updateShipment(trackingNumber, stringStatus);
        } catch (ResponseStatusException e) {
            exception = e;
        }

        // ASSERT
        assertNull(shipment);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetAllShipments() {

        // ARRANGE
        int firstId = 1;
        String firstOrigin = "Mumbai";
        String firstDestination = "Bangalore";
        LocalDateTime firstShipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);
        String firstTrackingNumber = UUID.randomUUID().toString();

        Shipment firstShipment = new Shipment();
        firstShipment.setId(firstId);
        firstShipment.setTrackingNumber(firstTrackingNumber);
        firstShipment.setOrigin(firstOrigin);
        firstShipment.setDestination(firstDestination);
        firstShipment.setShipmentDate(firstShipmentDate);
        firstShipment.setStatus(Status.CREATED);
        firstShipment.setLastUpdated(LocalDateTime.now());

        int secondId = 2;
        String secondOrigin = "Jaipur";
        String secondDestination = "Kolkata";
        LocalDateTime secondShipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);
        String secondTrackingNumber = UUID.randomUUID().toString();

        Shipment secondShipment = new Shipment();
        secondShipment.setId(secondId);
        secondShipment.setTrackingNumber(secondTrackingNumber);
        secondShipment.setOrigin(secondOrigin);
        secondShipment.setDestination(secondDestination);
        secondShipment.setShipmentDate(secondShipmentDate);
        secondShipment.setStatus(Status.CREATED);
        secondShipment.setLastUpdated(LocalDateTime.now());

        List<Shipment> shipments = new ArrayList<Shipment>();
        shipments.add(firstShipment);
        shipments.add(secondShipment);

        doReturn(shipments).when(shipmentRepository).findAll();

        ShipmentContract firstShipmentContract = new ShipmentContract();
        firstShipmentContract.setTrackingNumber(firstTrackingNumber);
        firstShipmentContract.setOrigin(firstOrigin);
        firstShipmentContract.setDestination(firstDestination);
        firstShipmentContract.setShipmentDate(firstShipmentDate);
        firstShipmentContract.setStatus(Status.CREATED);
        firstShipmentContract.setLastUpdated(firstShipment.getLastUpdated());

        ShipmentContract secondShipmentContract = new ShipmentContract();
        secondShipmentContract.setTrackingNumber(secondTrackingNumber);
        secondShipmentContract.setOrigin(secondOrigin);
        secondShipmentContract.setDestination(secondDestination);
        secondShipmentContract.setShipmentDate(secondShipmentDate);
        secondShipmentContract.setStatus(Status.CREATED);
        secondShipmentContract.setLastUpdated(secondShipment.getLastUpdated());

        List<ShipmentContract> expectedShipmentContracts = new ArrayList<ShipmentContract>();
        expectedShipmentContracts.add(firstShipmentContract);
        expectedShipmentContracts.add(secondShipmentContract);

        doReturn(firstShipmentContract).when(dataContractMapper).shipmentContractFactory(firstShipment);
        doReturn(secondShipmentContract).when(dataContractMapper).shipmentContractFactory(secondShipment);

        // ACT
        List<ShipmentContract> receivedShipmentContracts = shipmentService.getAllShipments();

        // ASSERT
        assertNotNull(receivedShipmentContracts);
        assertEquals(receivedShipmentContracts, expectedShipmentContracts);
    }

    @Test
    public void testGetShipmentByTrackingNumberWhenShipmentIsNotNull() {

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

        doReturn(shipment).when(shipmentRepository).findByTrackingNumber(trackingNumber);

        ShipmentContract expectedShipmentContract = new ShipmentContract();
        expectedShipmentContract.setTrackingNumber(trackingNumber);
        expectedShipmentContract.setOrigin(origin);
        expectedShipmentContract.setDestination(destination);
        expectedShipmentContract.setShipmentDate(shipmentDate);
        expectedShipmentContract.setStatus(Status.CREATED);
        expectedShipmentContract.setLastUpdated(shipment.getLastUpdated());

        doReturn(expectedShipmentContract).when(dataContractMapper).shipmentContractFactory(shipment);

        // ACT
        ShipmentContract recievedShipmentContract = shipmentService.getShipmentByTrackingNumber(trackingNumber);

        // ASSERT
        assertNotNull(recievedShipmentContract);
        assertEquals(recievedShipmentContract, expectedShipmentContract);

    }

    @Test
    public void testGetShipmentByTrackingNumberWhenShipmentIsNull() {

        // ARRANGE
        String trackingNumber = UUID.randomUUID().toString();
        Shipment shipment = null;

        doReturn(shipment).when(shipmentRepository).findByTrackingNumber(trackingNumber);

        // ACT
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.ACCEPTED);
        try {
            shipmentService.getShipmentByTrackingNumber(trackingNumber);
        } catch (ResponseStatusException e) {
            exception = e;
        }

        // ASSERT
        assertNull(shipment);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

    }

    @Test
    public void testRemoveShipmentByTrackingNumberWhenShipmentIsNotNull() {

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
        shipment.setStatus(Status.DELIVERED);
        shipment.setLastUpdated(LocalDateTime.now());

        doReturn(shipment).when(shipmentRepository).findByTrackingNumber(trackingNumber);

        doNothing().when(shipmentRepository).delete(shipment);

        String message = "The shipment has been deleted";

        doReturn(message).when(configureMessages).getShipmentDeletedMessage();

        MessageStatusContract expectedMessageStatusContract = new MessageStatusContract();
        expectedMessageStatusContract.setTrackingNumber(shipment.getTrackingNumber());
        expectedMessageStatusContract.setStatus(shipment.getStatus());
        expectedMessageStatusContract.setMessage(message);

        doReturn(expectedMessageStatusContract)
                .when(dataContractMapper)
                .messageStatusContractFactory(shipment, message);

        // ACT
        MessageStatusContract receivedMessageStatusContract = shipmentService
                .removeShipmentByTrackingNumber(trackingNumber);

        // ASSERT
        assertThat(shipmentRepository.findById(shipment.getId())).isEmpty();
        assertEquals(receivedMessageStatusContract, expectedMessageStatusContract);

    }

    @Test
    public void testRemoveShipmentByTrackingNumberWhenShipmentIsNull() {

        // ARRANGE
        String trackingNumber = UUID.randomUUID().toString();
        Shipment shipment = null;

        doReturn(shipment).when(shipmentRepository).findByTrackingNumber(trackingNumber);

        // ACT
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.ACCEPTED);
        try {
            shipmentService.removeShipmentByTrackingNumber(trackingNumber);
        } catch (ResponseStatusException e) {
            exception = e;
        }

        // ASSERT
        assertNull(shipment);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

    }
}
