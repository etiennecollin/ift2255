/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

public enum DataMap {
    BUYERS ("buyers.txt"),
    SELLERS ("sellers.txt"),
    PRODUCTS ("products.txt"),
    CARTS ("carts.txt"),
    ORDERS ("orders.txt"),
    REVIEWS ("reviews.txt"),
    LIKES ("likes.txt"),
    NOTIFICATIONS ("notifications.txt");

    private final String filename;

    DataMap(String filename) {
        this.filename = filename;
    }

    String getFilename() {
        return filename;
    }
}
