/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.io.Serializable;

public enum ITCategory implements Serializable {
    Computer("Computer"), Mouse("Mouse"), Keyboard("Keyboard"), ExternalHardDrive("External Hard Drive"), Other("Other");
    private final String name;

    ITCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
