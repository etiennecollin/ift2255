/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.io.Serializable;

/**
 * Enum representing different categories of stationery articles.
 * Each constant in this enum corresponds to a specific category with a name.
 */
public enum StationeryArticleCategory implements Serializable {
    /**
     * Category representing a notebook.
     */
    Notebook("Notebook"),
    /**
     * Category representing a pencil.
     */
    Pencil("Pencil"),
    /**
     * Category representing a highlighter.
     */
    Highlighter("Highlighter"),
    /**
     * Category representing other stationery articles.
     */
    Other("Other");
    /**
     * The name associated with each category.
     */
    private final String name;

    /**
     * Constructs a StationeryArticleCategory with the specified name.
     *
     * @param name The name of the category.
     */
    StationeryArticleCategory(String name) {
        this.name = name;
    }

    /**
     * Returns the name associated with the category.
     *
     * @return The name of the category.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
