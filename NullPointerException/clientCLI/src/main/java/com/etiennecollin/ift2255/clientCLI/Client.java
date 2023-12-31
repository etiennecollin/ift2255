/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    /**
     * The main method that initializes the UniShop instance and renders views.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        UniShop.getInstance().getRenderer().renderViews();
    }
}
