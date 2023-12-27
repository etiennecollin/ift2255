/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

/**
 * A base class representing a user with common attributes such as email, phone number, address, password hash, and likes.
 */
public class User extends DatabaseObject {
    /**
     * The email of the user.
     */
    private String email;
    /**
     * The phone number of the user.
     */
    private String phoneNumber;
    /**
     * The address of the user.
     */
    private String address;
    /**
     * The hash of the user's password.
     */
    private int passwordHash;
    /**
     * The number of likes received by the user.
     */
    private int likes;

    /**
     * Constructs a User object with the specified email, password hash, phone number, and address.
     *
     * @param email        The email of the user.
     * @param passwordHash The hash of the user's password.
     * @param phoneNumber  The phone number of the user.
     * @param address      The address of the user.
     */
    public User(String email, int passwordHash, String phoneNumber, String address) {
        super();
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.likes = 0;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The new email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return The phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber The new phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the address of the user.
     *
     * @return The address of the user.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     *
     * @param address The new address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the hash of the user's password.
     *
     * @return The password hash of the user.
     */
    public int getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash of the user.
     *
     * @param passwordHash The new password hash to set.
     */
    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets the number of likes received by the user.
     *
     * @return The number of likes received by the user.
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Sets the number of likes received by the user.
     *
     * @param likes The new number of likes to set.
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }
}
