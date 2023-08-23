package com.philips.shipment.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philips.shipment.contracts.MessageStatusContract;
import com.philips.shipment.contracts.ShipmentContract;
import com.philips.shipment.enums.Status;
import com.philips.shipment.services.ShipmentService;

@WebMvcTest(controllers = ShipmentController.class)
@ExtendWith(MockitoExtension.class)
public class ShipmentControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private ShipmentService shipmentService;

        @Test
        public void testCreate() throws Exception {

                // ARRANGE
                String origin = "Mumbai";
                String destination = "Bangalore";
                LocalDateTime shipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);

                MessageStatusContract expectedResponse = new MessageStatusContract();
                expectedResponse.setTrackingNumber(UUID.randomUUID().toString());
                expectedResponse.setStatus(Status.CREATED);
                expectedResponse.setMessage("The shipment has been created.");

                doReturn(expectedResponse).when(shipmentService).addShipment(origin, destination, shipmentDate);

                // ACT
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/shipments")
                                .param("origin", origin)
                                .param("destination", destination)
                                .param("shipmentDate", shipmentDate.toString()))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String content = result.getResponse().getContentAsString();
                MessageStatusContract response = objectMapper.readValue(content, MessageStatusContract.class);

                // ASSERT
                assertEquals(expectedResponse.getTrackingNumber(), response.getTrackingNumber());
                assertEquals(expectedResponse.getStatus(), response.getStatus());
                assertEquals(expectedResponse.getMessage(), response.getMessage());

        }

        @Test
        public void testUpdate() throws Exception {

                // ARRANGE
                String trackingNumber = UUID.randomUUID().toString();
                String status = "IN_TRANSIT";

                MessageStatusContract expectedResponse = new MessageStatusContract();
                expectedResponse.setTrackingNumber(trackingNumber);
                expectedResponse.setStatus(Status.valueOf(status));
                expectedResponse.setMessage("The shipment has been updated.");

                doReturn(expectedResponse).when(shipmentService).updateShipment(trackingNumber, status);

                // ACT
                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.patch("/api/shipments/{trackingNumber}", trackingNumber)
                                                .param("trackingNumber", trackingNumber)
                                                .param("status", status))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String content = result.getResponse().getContentAsString();
                MessageStatusContract response = objectMapper.readValue(content, MessageStatusContract.class);

                // ASSERT
                assertEquals(expectedResponse.getTrackingNumber(), response.getTrackingNumber());
                assertEquals(expectedResponse.getStatus(), response.getStatus());
                assertEquals(expectedResponse.getMessage(), response.getMessage());

        }

        @Test
        public void testGetAll() throws Exception {

                // ARRANGE
                String firstOrigin = "Mumbai";
                String firstDestination = "Bangalore";
                LocalDateTime firstShipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);
                String firstTrackingNumber = UUID.randomUUID().toString();
                LocalDateTime firstLastUpdated = LocalDateTime.now();

                ShipmentContract firstShipmentContract = new ShipmentContract();
                firstShipmentContract.setTrackingNumber(firstTrackingNumber);
                firstShipmentContract.setOrigin(firstOrigin);
                firstShipmentContract.setDestination(firstDestination);
                firstShipmentContract.setShipmentDate(firstShipmentDate);
                firstShipmentContract.setStatus(Status.CREATED);
                firstShipmentContract.setLastUpdated(firstLastUpdated);

                String secondOrigin = "Jaipur";
                String secondDestination = "Kolkata";
                LocalDateTime secondShipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);
                String secondTrackingNumber = UUID.randomUUID().toString();
                LocalDateTime secondLastUpdated = LocalDateTime.now();

                ShipmentContract secondShipmentContract = new ShipmentContract();
                secondShipmentContract.setTrackingNumber(secondTrackingNumber);
                secondShipmentContract.setOrigin(secondOrigin);
                secondShipmentContract.setDestination(secondDestination);
                secondShipmentContract.setShipmentDate(secondShipmentDate);
                secondShipmentContract.setStatus(Status.CREATED);
                secondShipmentContract.setLastUpdated(secondLastUpdated);

                List<ShipmentContract> expectedResponses = new ArrayList<ShipmentContract>();
                expectedResponses.add(firstShipmentContract);
                expectedResponses.add(secondShipmentContract);

                doReturn(expectedResponses).when(shipmentService).getAllShipments();

                // ACT
                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.get("/api/shipments"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String content = result.getResponse().getContentAsString();
                List<ShipmentContract> response = objectMapper.readValue(content, new TypeReference<>() {
                });

                // ASSERT
                assertEquals(expectedResponses.size(), response.size());

        }

        @Test
        public void testGetByTrackingNumber() throws Exception {

                // ARRANGE
                String trackingNumber = UUID.randomUUID().toString();
                String origin = "Mumbai";
                String destination = "Bangalore";
                LocalDateTime shipmentDate = LocalDateTime.of(2023, 8, 28, 0, 0);
                Status status = Status.DELIVERED;
                LocalDateTime lastUpdated = LocalDateTime.now();

                ShipmentContract expectedResponse = new ShipmentContract();
                expectedResponse.setTrackingNumber(trackingNumber);
                expectedResponse.setOrigin(origin);
                expectedResponse.setDestination(destination);
                expectedResponse.setShipmentDate(shipmentDate);
                expectedResponse.setStatus(status);
                expectedResponse.setLastUpdated(lastUpdated);

                doReturn(expectedResponse).when(shipmentService).getShipmentByTrackingNumber(trackingNumber);

                // ACT
                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.get("/api/shipments/{trackingNumber}", trackingNumber)
                                                .param("trackingNumber", trackingNumber))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String content = result.getResponse().getContentAsString();
                ShipmentContract response = objectMapper.readValue(content, ShipmentContract.class);

                // ASSERT
                assertEquals(expectedResponse.getTrackingNumber(), response.getTrackingNumber());
                assertEquals(expectedResponse.getOrigin(), response.getOrigin());
                assertEquals(expectedResponse.getDestination(), response.getDestination());
                assertEquals(expectedResponse.getShipmentDate(), response.getShipmentDate());
                assertEquals(expectedResponse.getStatus(), response.getStatus());
                assertEquals(expectedResponse.getLastUpdated(), response.getLastUpdated());

        }

        @Test
        public void testDeleteByTrackingNumber() throws Exception {

                // ARRANGE
                String trackingNumber = UUID.randomUUID().toString();

                MessageStatusContract expectedResponse = new MessageStatusContract();
                expectedResponse.setTrackingNumber(trackingNumber);
                expectedResponse.setStatus(Status.CANCELLED);
                expectedResponse.setMessage("The shipment has been deleted.");

                doReturn(expectedResponse).when(shipmentService).removeShipmentByTrackingNumber(trackingNumber);

                // ACT
                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders
                                                .delete("/api/shipments/{trackingNumber}", trackingNumber)
                                                .param("trackingNumber", trackingNumber))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String content = result.getResponse().getContentAsString();
                MessageStatusContract response = objectMapper.readValue(content, MessageStatusContract.class);

                // ASSERT
                // ASSERT
                assertEquals(expectedResponse.getStatus(), response.getStatus());
                assertEquals(expectedResponse.getMessage(), response.getMessage());

        }
}
