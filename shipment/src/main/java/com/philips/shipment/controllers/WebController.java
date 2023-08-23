package com.philips.shipment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/addShipment")
    public String addShipment() {
        return "addShipment";
    }

    @RequestMapping("/updateShipment")
    public String updateShipment() {
        return "updateShipment";
    }

    @RequestMapping("/viewShipment")
    public String viewShipment() {
        return "viewShipment";
    }

    @RequestMapping("/deleteShipment")
    public String deleteShipment() {
        return "deleteShipment";
    }
}
