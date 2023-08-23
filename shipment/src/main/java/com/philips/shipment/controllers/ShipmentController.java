package com.philips.shipment.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;
import com.philips.shipment.services.ShipmentService;

@RestController
public class ShipmentController {

    @Autowired
    ShipmentService shipmentService;

    // POST Method
    @PostMapping("/api/shipments")
    public MessageStatusContract create(@RequestParam("origin") String origin,
            @RequestParam("destination") String destination,
            @RequestParam("shipmentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime shipmentDate) {
        return shipmentService.addShipment(origin, destination, shipmentDate);
    }

    // PATCH Method
    @PatchMapping("/api/shipments/{trackingNumber}")
    public MessageStatusContract update(
            @RequestParam("trackingNumber") @PathVariable("trackingNumber") String trackingNumber,
            @RequestParam("status") String status) {
        return shipmentService.updateShipment(trackingNumber, status);
    }

    // GET Method
    @GetMapping("/api/shipments")
    public List<ShipmentContract> getAll() {
        return shipmentService.getAllShipments();
    }

    // GET Method
    @GetMapping("/api/shipments/{trackingNumber}")
    public ShipmentContract getByTrackingNumber(
            @RequestParam("trackingNumber") @PathVariable("trackingNumber") String trackingNumber) {
        return shipmentService.getShipmentByTrackingNumber(trackingNumber);
    }

    // DELETE Method
    @DeleteMapping("/api/shipments/{trackingNumber}")
    public MessageStatusContract deleteByTrackingNumber(
            @RequestParam("trackingNumber") @PathVariable("trackingNumber") String trackingNumber) {
        return shipmentService.removeShipmentByTrackingNumber(trackingNumber);
    }
}
