/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

/**
 * Enum representing mappings between different types of data and their corresponding file names.
 */
public enum DataMap {
    /**
     * Represents the mapping between the "Buyer" data type and the "buyers.txt" file.
     */
    BUYERS("buyers.txt"),
    /**
     * Represents the mapping between the "Seller" data type and the "sellers.txt" file.
     */
    SELLERS("sellers.txt"),
    /**
     * Represents the mapping between the "Product" data type and the "products.txt" file.
     */
    PRODUCTS("products.txt"),
    /**
     * Represents the mapping between the "Cart" data type and the "carts.txt" file.
     */
    CARTS("carts.txt"),
    /**
     * Represents the mapping between the "Order" data type and the "orders.txt" file.
     */
    ORDERS("orders.txt"),
    /**
     * Represents the mapping between the "Review" data type and the "reviews.txt" file.
     */
    REVIEWS("reviews.txt"),
    /**
     * Represents the mapping between the "Like" data type and the "likes.txt" file.
     */
    LIKES("likes.txt"),
    /**
     * Represents the mapping between the "Notification" data type and the "notifications.txt" file.
     */
    NOTIFICATIONS("notifications.txt"),
    /**
     * Represents the mapping between the "Ticket" data type and the "tickets.txt" file.
     */
    TICKETS("tickets.txt");
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
