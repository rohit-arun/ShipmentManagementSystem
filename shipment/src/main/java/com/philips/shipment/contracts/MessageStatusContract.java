package com.philips.shipment.contracts;

import org.springframework.stereotype.Component;
import com.philips.shipment.enums.Status;

@Component
public class MessageStatusContract {

    private String trackingNumber;
    private Status status;
    private String message;

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
