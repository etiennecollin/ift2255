/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

/**
 * Represents a buyer in the system, extending the User class.
 * Each instance of this class corresponds to a specific buyer with additional information.
 */
public class Buyer extends User {
    /**
     * The last name of the buyer.
     */
    private String lastName;
    /**
     * The first name of the buyer.
     */
    private String firstName;
    /**
     * The unique username of the buyer. This should be unique across all buyers.
     */
    private String username;
    /**
     * The amount of fidelity points associated with the buyer.
     */
    private int fidelityPoints;

    /**
     * Constructs a new Buyer with the specified information.
     *
     * @param username       The unique username of the buyer.
     * @param passwordHash   The hashed password of the buyer.
     * @param firstName      The first name of the buyer.
     * @param lastName       The last name of the buyer.
     * @param email          The email address of the buyer.
     * @param phoneNumber    The phone number of the buyer.
     * @param address        The address of the buyer.
     * @param fidelityPoints The initial amount of fidelity points for the buyer.
     */
    public Buyer(String username, int passwordHash, String firstName, String lastName, String email, String phoneNumber, String address, int fidelityPoints) {
        super(email, passwordHash, phoneNumber, address);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUsername(username);
        this.setFidelityPoints(fidelityPoints);
    }

    /**
     * Gets the unique username of the buyer.
     *
     * @return The buyer's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the unique username of the buyer.
     *
     * @param username The new username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the first name of the buyer.
     *
     * @return The buyer's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the buyer.
     *
     * @param firstName The new first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the buyer.
     *
     * @return The buyer's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the buyer.
     *
     * @param lastName The new last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the amount of fidelity points associated with the buyer.
     *
     * @return The buyer's fidelity points.
     */
    public int getFidelityPoints() {
        return fidelityPoints;
    }

    /**
     * Sets the amount of fidelity points for the buyer.
     *
     * @param fidelityPoints The new amount of fidelity points to set.
     */
    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }
}