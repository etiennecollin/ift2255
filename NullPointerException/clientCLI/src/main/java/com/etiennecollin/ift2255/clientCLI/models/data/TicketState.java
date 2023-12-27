/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.io.Serializable;

/**
 * Enum representing the possible states of a ticket.
 */
public enum TicketState implements Serializable {
    /**
     * Ticket state: Open (Manual)
     */
    OpenManual("Open"),
    /**
     * Ticket state: Open (Auto)
     */
    OpenAuto("Open"),
    /**
     * Ticket state: Return in Transit
     */
    ReturnInTransit("Return in transit"),
    /**
     * Ticket state: Return Received
     */
    ReturnReceived("Return received"),
    /**
     * Ticket state: Replacement in Transit
     */
    ReplacementInTransit("Replacement in transit"),
    /**
     * Ticket state: Closed
     */
    Closed("Closed"),
    /**
     * Ticket state: Cancelled
     */
    Cancelled("Cancelled");
    /**
     * The name associated with the ticket state.
     */
    private final String name;

    /**
     * Constructs a TicketState enum with the specified name.
     *
     * @param name The name associated with the ticket state.
     */
    TicketState(String name) {
        this.name = name;
    }

    //    public static ArrayList<String> getOptions() {
    //        ArrayList<String> options = new ArrayList<>();
    //        for (TicketState option : TicketState.values()) {
    //            options.add(option.toString());
    //        }
    //        return options;
    //    }

    /**
     * Gets the string representation of the ticket state.
     *
     * @return The string representation of the ticket state.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
