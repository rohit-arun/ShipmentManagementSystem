package com.philips.shipment.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.philips.shipment.configuration.ConfigureMessages;
import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;
import com.philips.shipment.enums.Status;
import com.philips.shipment.factories.ShipmentServiceFactory;
import com.philips.shipment.models.Shipment;
import com.philips.shipment.repositories.ShipmentRepository;

@Service
public class ShipmentService implements ShipmentServiceFactory {

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    DataContractMapper dataContractMapper;

    @Autowired
    DataModelMapper dataModelMapper;

    @Autowired
    ConfigureMessages configureMessages;

    // Create Shipment
    public MessageStatusContract addShipment(String origin, String destination, LocalDateTime shipmentDate) {
        Shipment shipment = dataModelMapper.shipmentFactory(origin, destination, shipmentDate);
        shipmentRepository.save(shipment);
        return dataContractMapper.messageStatusContractFactory(shipment, configureMessages.getShipmentCreatedMessage());
    }

    // Update Shipment
    public MessageStatusContract updateShipment(String trackingNumber, String status) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber);

        if (shipment != null) {
            shipment.setStatus(Status.valueOf(status));
            shipment.setLastUpdated(LocalDateTime.now());
            shipmentRepository.save(shipment);
            return dataContractMapper.messageStatusContractFactory(shipment,
                    configureMessages.getShipmentUpdatedMessage());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found for id: " + trackingNumber);
        }
    }

    // Get All
    public List<ShipmentContract> getAllShipments() {
        List<Shipment> shipments = new ArrayList<Shipment>();
        shipmentRepository.findAll().forEach(shipment -> shipments.add(shipment));
        List<ShipmentContract> shipmentsContract = new ArrayList<ShipmentContract>();

        for (Shipment shipment : shipments) {
            shipmentsContract.add(dataContractMapper.shipmentContractFactory(shipment));
        }
        return shipmentsContract;
    }

    // Get Shipment by trackingNumber
    public ShipmentContract getShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber);

        if (shipment != null) {
            ShipmentContract shipmentContract = dataContractMapper.shipmentContractFactory(shipment);
            return shipmentContract;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found for id: " + trackingNumber);
        }
    }

    // Delete Shipment by trackingNumber
    public MessageStatusContract removeShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber);
        if (shipment != null) {
            shipmentRepository.delete(shipment);
            return dataContractMapper
                    .messageStatusContractFactory(shipment, configureMessages.getShipmentDeletedMessage());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found for id: " + trackingNumber);
        }
    }
}
