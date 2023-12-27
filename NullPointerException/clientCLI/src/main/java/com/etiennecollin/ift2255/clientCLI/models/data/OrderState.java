/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the possible states of an order, implementing Serializable.
 */
public enum OrderState implements Serializable {
    /**
     * The order is in the production stage.
     */
    InProduction("In Production"),
    /**
     * The order is in transit.
     */
    InTransit("In Transit"),
    /**
     * The order has been delivered.
     */
    Delivered("Delivered"),
    /**
     * The order has been cancelled.
     */
    Cancelled("Cancelled");
    /**
     * The name associated with the order state.
     */
    private final String name;

    /**
     * Constructs an OrderState object with the given name.
     *
     * @param name The name associated with the order state.
     */
    OrderState(String name) {
        this.name = name;
    }

    /**
     * Gets a list of all order state options as strings.
     *
     * @return ArrayList containing all order state options.
     */
    public static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (OrderState option : OrderState.values()) {
            options.add(option.toString());
        }
        return options;
    }

    /**
     * Returns the string representation of the order state.
     *
     * @return The name associated with the order state.
     */
    @Override
    public String toString() {
        return this.name;
    }
}

