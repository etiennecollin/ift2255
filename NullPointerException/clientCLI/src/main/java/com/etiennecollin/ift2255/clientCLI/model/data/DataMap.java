/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

/**
 * Enum representing mappings between different types of data and their corresponding file names.
 */
public enum DataMap {
    BUYERS("buyers.txt"), SELLERS("sellers.txt"), PRODUCTS("products.txt"), CARTS("carts.txt"), ORDERS("orders.txt"), REVIEWS("reviews.txt"), LIKES("likes.txt"), NOTIFICATIONS("notifications.txt"), TICKETS("tickets.txt");
    /**
     * The filename associated with the data type.
     */
    private final String filename;

    /**
     * Constructs a DataMap with the specified filename.
     *
     * @param filename The filename associated with the data type.
     */
    DataMap(String filename) {
        this.filename = filename;
    }

    /**
     * Gets the filename associated with the data type.
     *
     * @return The filename.
     */
    String getFilename() {
        return filename;
    }
}
