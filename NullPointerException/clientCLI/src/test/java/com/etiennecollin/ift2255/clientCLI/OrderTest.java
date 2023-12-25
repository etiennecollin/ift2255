/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */
package com.etiennecollin.ift2255.clientCLI;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testSetInTransit() {
        // Create a mock Order for testing
        Order order = new Order(0, 0, PaymentMethod.CASH,
                new ArrayList<>(), "", "", "", "", "", "", YearMonth.of(2023, 12), "",
                new Buyer("", ""), new Seller("", ""));
        // Set the order in transit for the first time
        String firstShippingCompany = "FastShip";
        String firstTrackingNumber = "ABC123";
        LocalDate firstExpectedDeliveryDate = LocalDate.now().plusDays(3);

        order.setInTransit(firstShippingCompany, firstTrackingNumber, firstExpectedDeliveryDate);

        // Assertions for the first call
        assertEquals(OrderState.InTransit, order.getState()); // Check if the order state is set to InTransit
        assertNotNull(order.getShipment()); // Check if the shipment object is created
        assertEquals(firstShippingCompany, order.getShipment().getShippingCompany()); // Check if shipping company is set correctly
        assertEquals(firstTrackingNumber, order.getShipment().getTrackingNumber()); // Check if tracking number is set correctly
        assertEquals(firstExpectedDeliveryDate, order.getShipment().getExpectedDeliveryDate()); // Check if expected delivery date is set correctly
        assertEquals(1, order.getBuyer().getNotifications().size()); // Check if a notification is added to the buyer
        // Set the order in transit for the second time
        String secondShippingCompany = "ExpressShip";
        String secondTrackingNumber = "XYZ789";
        LocalDate secondExpectedDeliveryDate = LocalDate.now().plusDays(2);

        order.setInTransit(secondShippingCompany, secondTrackingNumber, secondExpectedDeliveryDate);

        // Assertions for the second call
        assertEquals(OrderState.InTransit, order.getState()); // Check if the order state remains InTransit after the second call
        assertNotNull(order.getShipment()); // Check if the shipment object is still present
        assertEquals(secondShippingCompany, order.getShipment().getShippingCompany()); // Check if shipping company is updated correctly in the second call
        assertEquals(secondTrackingNumber, order.getShipment().getTrackingNumber()); // Check if tracking number is updated correctly in the second call
        assertEquals(secondExpectedDeliveryDate, order.getShipment().getExpectedDeliveryDate()); // Check if expected delivery date is updated correctly in the second call
        assertEquals(2, order.getBuyer().getNotifications().size()); // Check if a new notification is added to the buyer in the second call
    }
}

