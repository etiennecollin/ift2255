/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import java.io.Serializable;

public enum StationeryArticleCategory implements Serializable {
    Notebook("Notebook"), Pencil("Pencil"), Highlighter("Highlighter"), Other("Other");
    private final String name;

    StationeryArticleCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
