/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.io.Serializable;

public enum TicketState implements Serializable {
    OpenManual("Open"), OpenAuto("Open"), ReturnInTransit("Return in transit"), ReturnReceived("Return received"), ReplacementInTransit("Replacement in transit"), Closed("Closed"), Cancelled("Cancelled");
    private final String name;

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

    @Override
    public String toString() {
        return this.name;
    }
}
