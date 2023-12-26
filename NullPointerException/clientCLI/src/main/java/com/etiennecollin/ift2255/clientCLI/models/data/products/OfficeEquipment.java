/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data.products;

import java.util.UUID;

/**
 * Represents an office equipment product extending the general Product class.
 *
 * This class includes specific attributes such as brand and models for office equipment.
 */
public class OfficeEquipment extends Product {
    /**
     * The brand of the office equipment.
     */
    private String brand;
    /**
     * The models of the office equipment.
     */
    private String model;

    /**
     * Constructs an OfficeEquipment object with specified attributes.
     *
     * @param price          The price of the office equipment.
     * @param quantity       The quantity of the office equipment available.
     * @param title          The title or name of the office equipment.
     * @param description    The description of the office equipment.
     * @param sellerId       The UUID of the seller selling the office equipment.
     * @param fidelityPoints The fidelity points associated with the office equipment.
     * @param brand          The brand of the office equipment.
     * @param model          The models of the office equipment.
     * @param subCategory    The sub-category of office equipment (e.g., Table, Chair).
     */
    public OfficeEquipment(int price, int quantity, String title, String description, UUID sellerId, int fidelityPoints, String brand, String model, OfficeEquipmentCategory subCategory) {
        super(price, quantity, title, description, ProductCategory.OfficeEquipment, subCategory, sellerId, fidelityPoints);
        this.brand = brand;
        this.model = model;
    }

    /**
     * Gets the brand of the office equipment.
     *
     * @return The brand of the office equipment.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the office equipment.
     *
     * @param brand The new brand to set for the office equipment.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the models of the office equipment.
     *
     * @return The models of the office equipment.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the models of the office equipment.
     *
     * @param model The new models to set for the office equipment.
     */
    public void setModel(String model) {
        this.model = model;
    }
}
