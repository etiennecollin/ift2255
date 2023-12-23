/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import java.io.Serializable;

public enum LearningResourceType implements Serializable {
    Printed("Printed"), Electronic("Electronic");
    private final String name;

    LearningResourceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
