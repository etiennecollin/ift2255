/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

public class Buyer extends User {
    private String lastName;
    private String firstName;
    private String username; // Unique
    private int fidelityPoints;

    public Buyer(String username, int passwordHash, String firstName, String lastName, String email, String phoneNumber, String address, int fidelityPoints) {
        super(email, passwordHash, phoneNumber, address);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUsername(username);
        this.setFidelityPoints(fidelityPoints);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }
}