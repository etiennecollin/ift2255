/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.io.Serializable;

/**
 * Enum representing the possible causes of a ticket.
 */
public enum TicketCause implements Serializable {
    /**
     * Ticket cause: Malfunctioning Product
     */
    MalfunctioningProduct("Malfunctioning product"),
    /**
     * Ticket cause: Other
     */
    Other("Other");
    /**
     * The name associated with the ticket cause.
     */
    private final String name;

    /**
     * Constructs a TicketCause enum with the specified name.
     *
     * @param name The name associated with the ticket cause.
     */
    TicketCause(String name) {
        this.name = name;
    }

    //    public static ArrayList<String> getOptions() {
    //        ArrayList<String> options = new ArrayList<>();
    //        for (TicketCause option : TicketCause.values()) {
    //            options.add(option.toString());
    //        }
    //        return options;
    //    }

    /**
     * Gets the string representation of the ticket cause.
     *
     * @return The string representation of the ticket cause.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
