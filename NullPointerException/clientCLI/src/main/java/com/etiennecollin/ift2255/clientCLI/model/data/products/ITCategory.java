/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.io.Serializable;

/**
 * Represents the categories of IT (Information Technology) products.
 * <p>
 * This enum includes categories such as Computer, Mouse, Keyboard, External Hard Drive, and Other.
 */
public enum ITCategory implements Serializable {
    /**
     * The category for computers.
     */
    Computer("Computer"),
    /**
     * The category for mice (computer mice).
     */
    Mouse("Mouse"),
    /**
     * The category for keyboards.
     */
    Keyboard("Keyboard"),
    /**
     * The category for external hard drives.
     */
    ExternalHardDrive("External Hard Drive"),
    /**
     * The category for other IT products not covered by specific categories.
     */
    Other("Other");
    /**
     * The name associated with the IT category.
     */
    private final String name;

    /**
     * Constructs an ITCategory enum with a specified name.
     *
     * @param name The name associated with the IT category.
     */
    ITCategory(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the IT category.
     *
     * @return The name of the IT category.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
