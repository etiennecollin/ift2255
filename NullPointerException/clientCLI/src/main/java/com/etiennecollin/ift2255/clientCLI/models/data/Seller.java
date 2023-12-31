/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

/**
 * Represents a seller in the system, extending the User class.
 */
public class Seller extends User {
    /**
     * The unique name of the seller.
     */
    private String name; // Unique

    /**
     * Constructs a Seller object with the given parameters.
     *
     * @param name         The unique name of the seller.
     * @param passwordHash The hash of the seller's password.
     * @param email        The email of the seller.
     * @param phoneNumber  The phone number of the seller.
     * @param address      The address of the seller.
     */
    public Seller(String name, int passwordHash, String email, String phoneNumber, String address) {
        super(email, passwordHash, phoneNumber, address);
        setName(name);
    }

    /**
     * Gets the unique name of the seller.
     *
     * @return The unique name of the seller.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the unique name of the seller.
     *
     * @param name The unique name of the seller.
     */
    public void setName(String name) {
        this.name = name;
    }
}
