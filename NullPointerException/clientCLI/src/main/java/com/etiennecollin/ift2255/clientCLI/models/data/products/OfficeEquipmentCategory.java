/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data.products;

import java.io.Serializable;

/**
 * Enumeration representing categories for office equipment.
 * <p>
 * Each category has a corresponding name.
 */
public enum OfficeEquipmentCategory implements Serializable {
    /**
     * Represents the category of a table.
     */
    Table("Table"),
    /**
     * Represents the category of a chair.
     */
    Chair("Chair"),
    /**
     * Represents the category of a lamp.
     */
    Lamp("Lamp"),
    /**
     * Represents the category of other office equipment.
     */
    Other("Other");
    /**
     * The name of the office equipment category.
     */
    private final String name;

    /**
     * Constructor for an OfficeEquipmentCategory with a specified name.
     *
     * @param name The name of the office equipment category.
     */
    OfficeEquipmentCategory(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the office equipment category.
     *
     * @return The name of the office equipment category.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
