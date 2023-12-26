/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.io.Serializable;

/**
 * Represents the type of learning resource, whether it is printed or electronic.
 * <p>
 * This enum provides options for differentiating between types of learning resources.
 */
public enum LearningResourceType implements Serializable {
    /**
     * Indicates a printed learning resource type.
     */
    Printed("Printed"),
    /**
     * Indicates an electronic learning resource type.
     */
    Electronic("Electronic");
    /**
     * The name associated with the learning resource type.
     */
    private final String name;

    /**
     * Constructs a LearningResourceType enum constant with a specified name.
     *
     * @param name The name associated with the learning resource type.
     */
    LearningResourceType(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the learning resource type.
     *
     * @return The string representation of the learning resource type.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
