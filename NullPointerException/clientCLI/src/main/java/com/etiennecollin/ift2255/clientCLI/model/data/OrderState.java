/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.io.Serializable;
import java.util.ArrayList;

public enum OrderState implements Serializable {
    InProduction("In Production"), InTransit("In Transit"), Delivered("Delivered"), Cancelled("Cancelled");
    private final String name;

    OrderState(String name) {
        this.name = name;
    }

    public static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (com.etiennecollin.ift2255.clientCLI.classes.OrderState option : com.etiennecollin.ift2255.clientCLI.classes.OrderState.values()) {
            options.add(option.toString());
        }
        return options;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

