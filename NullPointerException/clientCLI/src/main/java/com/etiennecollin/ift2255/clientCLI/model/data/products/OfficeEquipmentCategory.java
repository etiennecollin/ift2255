/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.io.Serializable;

public enum OfficeEquipmentCategory implements Serializable {
    Table("Table"), Chair("Chair"), Lamp("Lamp"), Other("Other");
    private final String name;

    OfficeEquipmentCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
