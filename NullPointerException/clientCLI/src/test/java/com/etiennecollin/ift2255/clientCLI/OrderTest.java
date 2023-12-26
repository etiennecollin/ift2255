/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.model.data.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The OrderTest class contains unit tests for the Order class, focusing on the setInTransit, setInProduction, and setDelivered methods.
 * It ensures the proper functionality of order state transitions and associated notifications.
 */
public class OrderTest {
    /**
     * Tests the setInTransit method in the Order class.
     */
    @Test
    public void testSetInTransit() {
        // Create a mock Order for testing
        Buyer buyer = new Buyer("", "".hashCode(), "", "", "", "", "", 0);
        Seller seller = new Seller("", "".hashCode(), "", "", "");
        PayementMethod payementMethod = new PayementMethod(0, 0, 0);
        Order order = new Order(new ArrayList<>(), 0, 0, payementMethod, "", "", "", "", "", "", YearMonth.of(2023, 12), "", buyer.getId(), seller.getId());

        String firstShippingCompany = "FastShip";
        String firstTrackingNumber = "ABC123";
        LocalDate firstExpectedDeliveryDate = LocalDate.now().plusDays(3);

        order.setState(OrderState.InTransit);
        order.setShipment(new Shipment(firstTrackingNumber, firstExpectedDeliveryDate, firstShippingCompany));

        // Assertions for the first call
        assertEquals(OrderState.InTransit, order.getState()); // Check if the order state is set to InTransit
        assertNotNull(order.getShipment()); // Check if the shipment object is created
        assertEquals(firstShippingCompany, order.getShipment().getShippingCompany()); // Check if shipping company is set correctly
        assertEquals(firstTrackingNumber, order.getShipment().getTrackingNumber()); // Check if tracking number is set correctly
        assertEquals(firstExpectedDeliveryDate, order.getShipment().getExpectedDeliveryDate()); // Check if expected delivery date is set correctly
        // TODO: Use new MVC model to test notifications
        // assertEquals(1, buyer.getNotifications().size()); // Check if a notification is added to the buyer

        // Set the order in transit for the second time
        String secondShippingCompany = "ExpressShip";
        String secondTrackingNumber = "XYZ789";
        LocalDate secondExpectedDeliveryDate = LocalDate.now().plusDays(2);

        order.setState(OrderState.InTransit);
        order.setShipment(new Shipment(secondTrackingNumber, secondExpectedDeliveryDate, secondShippingCompany));

        // Assertions for the second call
        assertEquals(OrderState.InTransit, order.getState()); // Check if the order state remains InTransit after the second call
        assertNotNull(order.getShipment()); // Check if the shipment object is still present
        assertEquals(secondShippingCompany, order.getShipment().getShippingCompany()); // Check if shipping company is updated correctly in the second call
        assertEquals(secondTrackingNumber, order.getShipment().getTrackingNumber()); // Check if tracking number is updated correctly in the second call
        assertEquals(secondExpectedDeliveryDate, order.getShipment().getExpectedDeliveryDate()); // Check if expected delivery date is updated correctly in the second call
        // TODO: Use new MVC model to test notifications
        // assertEquals(2, order.getBuyer().getNotifications().size()); // Check if a new notification is added to the buyer in the second call
    }

    /**
     * Tests the setInProduction method in the Order class.
     */
    @Test
    public void testSetInProduction() {
        // Create a mock Order for testing
        Buyer buyer = new Buyer("", "".hashCode(), "", "", "", "", "", 0);
        Seller seller = new Seller("", "".hashCode(), "", "", "");
        PayementMethod payementMethod = new PayementMethod(0, 0, 0);
        Order order = new Order(new ArrayList<>(), 0, 0, payementMethod, "", "", "", "", "", "", YearMonth.of(2023, 12), "", buyer.getId(), seller.getId());

        // Set the order in production
        order.setState(OrderState.InProduction);

        // Assertions to check the state
        assertEquals(OrderState.InProduction, order.getState()); // Check if the order state is set to InProduction

        // Assertions for the notificatiouns
        // TODO: Use new MVC model to test notifications
        // assertEquals(1, order.getBuyer().getNotifications().size()); // Check if a notification is added to the buyer
        // assertEquals("Your order is now in production", order.getBuyer().getNotifications().get(0).getTitle());
    }

    /**
     * Tests the setDelivered method in the Order class.
     */
    @Test
    public void testSetDelivered() {
        // Create a mock Order for testing
        Buyer buyer = new Buyer("", "".hashCode(), "", "", "", "", "", 0);
        Seller seller = new Seller("", "".hashCode(), "", "", "");
        PayementMethod payementMethod = new PayementMethod(0, 0, 0);
        Order order = new Order(new ArrayList<>(), 0, 0, payementMethod, "", "", "", "", "", "", YearMonth.of(2023, 12), "", buyer.getId(), seller.getId());

        // Set the order as delivered
        order.setState(OrderState.Delivered);

        // Assertions to check the state
        assertEquals(OrderState.Delivered, order.getState()); // Check if the order state is set to Delivered

        // Notification Assertion
        // TODO: Use new MVC model to test notifications
        // assertEquals(1, order.getBuyer().getNotifications().size());
        // assertEquals("Your order is now delivered", order.getBuyer().getNotifications().get(0).getTitle());
    }
}
