/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.io.Serializable;

/**
 * Enumeration representing genres for books or manuals.
 * <p>
 * Each constant in this enumeration represents a specific genre, such as Novel, Documentary, Comic, Textbook, or Other.
 */
public enum BookOrManualGenre implements Serializable {
    /**
     * Represents the genre of a Novel.
     */
    Novel("Novel"),
    /**
     * Represents the genre of a Documentary.
     */
    Documentary("Documentary"),
    /**
     * Represents the genre of a Comic.
     */
    Comic("Comic"),
    /**
     * Represents the genre of a Textbook.
     */
    Textbook("Textbook"),
    /**
     * Represents a generic or other genre not covered by the specific constants.
     */
    Other("Other");
    /**
     * The name associated with each genre constant.
     */
    private final String name;

    /**
     * Constructs a BookOrManualGenre with a specified name.
     *
     * @param name The name associated with the genre.
     */
    BookOrManualGenre(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the genre.
     *
     * @return The name of the genre.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
