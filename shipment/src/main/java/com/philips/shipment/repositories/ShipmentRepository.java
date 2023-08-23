package com.philips.shipment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.philips.shipment.models.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    Shipment findByTrackingNumber(String trackingNumber);
}
