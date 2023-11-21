/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.io.Serializable;
import java.time.LocalDate;

public class Shipment implements Serializable {
    private String shippingCompany;
    private boolean isDeliveryConfirmed;
    private String trackingNumber;
    private LocalDate creationDate;
    private LocalDate expectedDeliveryDate;

    public Shipment(String trackingNumber, LocalDate expectedDeliveryDate, String shippingCompany) {
        this.isDeliveryConfirmed = false;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.shippingCompany = shippingCompany;
        this.creationDate = LocalDate.now();
        this.trackingNumber = trackingNumber;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public boolean isDeliveryConfirmed() {
        return isDeliveryConfirmed;
    }

    public void confirmDelivery() {
        isDeliveryConfirmed = true;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
