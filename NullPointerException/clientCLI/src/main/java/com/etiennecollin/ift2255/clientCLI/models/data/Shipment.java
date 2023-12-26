/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a shipment in the system.
 */
public class Shipment implements Serializable {
    /**
     * The shipping company associated with the shipment.
     */
    private String shippingCompany;
    /**
     * A flag indicating whether the delivery is confirmed or not.
     */
    private boolean isDeliveryConfirmed;
    /**
     * The tracking number of the shipment.
     */
    private String trackingNumber;
    /**
     * The date when the shipment was created.
     */
    private LocalDate creationDate;
    /**
     * The expected delivery date of the shipment.
     */
    private LocalDate expectedDeliveryDate;

    /**
     * Constructs a Shipment object with the given parameters.
     *
     * @param trackingNumber       The tracking number of the shipment.
     * @param expectedDeliveryDate The expected delivery date of the shipment.
     * @param shippingCompany      The shipping company associated with the shipment.
     */
    public Shipment(String trackingNumber, LocalDate expectedDeliveryDate, String shippingCompany) {
        this.isDeliveryConfirmed = false;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.shippingCompany = shippingCompany;
        this.creationDate = LocalDate.now();
        this.trackingNumber = trackingNumber;
    }

    /**
     * Gets the shipping company associated with the shipment.
     *
     * @return The shipping company associated with the shipment.
     */
    public String getShippingCompany() {
        return shippingCompany;
    }

    /**
     * Sets the shipping company associated with the shipment.
     *
     * @param shippingCompany The shipping company associated with the shipment.
     */
    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    /**
     * Gets the expected delivery date of the shipment.
     *
     * @return The expected delivery date of the shipment.
     */
    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    /**
     * Sets the expected delivery date of the shipment.
     *
     * @param expectedDeliveryDate The expected delivery date of the shipment.
     */
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    /**
     * Gets the tracking number of the shipment.
     *
     * @return The tracking number of the shipment.
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * Sets the tracking number of the shipment.
     *
     * @param trackingNumber The tracking number of the shipment.
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /**
     * Checks if the delivery is confirmed.
     *
     * @return True if the delivery is confirmed, false otherwise.
     */
    public boolean isDeliveryConfirmed() {
        return isDeliveryConfirmed;
    }

    /**
     * Confirms the delivery of the shipment.
     */
    public void confirmDelivery() {
        isDeliveryConfirmed = true;
    }

    /**
     * Gets the creation date of the shipment.
     *
     * @return The creation date of the shipment.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the shipment.
     *
     * @param creationDate The creation date of the shipment.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
