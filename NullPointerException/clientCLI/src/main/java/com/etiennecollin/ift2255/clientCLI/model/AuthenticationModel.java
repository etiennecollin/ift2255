/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.*;

import java.util.List;
import java.util.UUID;


/**
 * The {@code AuthenticationModel} class provides methods for authenticating users,
 * registering new buyers and sellers, and checking the availability of usernames.
 */
public class AuthenticationModel {
    /**
     * The database instance used to interact with persistent data.
     */
    private final Database db;

    /**
     * Constructs an {@code AuthenticationModel} with the specified database instance.
     *
     * @param database The database instance.
     */
    public AuthenticationModel(Database database) {
        this.db = database;
    }

    /**
     * Authenticates a buyer based on the provided username and password.
     *
     * @param username The buyer's username.
     * @param password The buyer's password.
     * @return An {@code OperationResult} indicating the success or failure of the authentication.
     */
    public OperationResult authenticateBuyer(String username, String password) {
        List<Buyer> matchedBuyers = this.db.get(DataMap.BUYERS, (entry) -> entry.getUsername().equalsIgnoreCase(username) && entry.getPasswordHash() == password.hashCode());

        if (!matchedBuyers.isEmpty()) {
            Buyer buyer = matchedBuyers.get(0);
            Session.createSession(buyer.getId(), UserType.Buyer);

            return new OperationResult(true, "");
        } else {
            return new OperationResult(false, "Username or password is incorrect.");
        }
    }

    /**
     * Authenticates a seller based on the provided name and password.
     *
     * @param name     The seller's name.
     * @param password The seller's password.
     * @return An {@code OperationResult} indicating the success or failure of the authentication.
     */
    public OperationResult authenticateSeller(String name, String password) {
        List<Seller> matchedSellers = this.db.get(DataMap.SELLERS, (entry) -> entry.getName().equalsIgnoreCase(name) && entry.getPasswordHash() == password.hashCode());

        if (!matchedSellers.isEmpty()) {
            Seller seller = matchedSellers.get(0);
            Session.createSession(seller.getId(), UserType.Seller);

            return new OperationResult(true, "");
        } else {
            return new OperationResult(false, "Username or password is incorrect.");
        }
    }

    /**
     * Registers a new buyer with the specified information.
     *
     * @param username    The buyer's username.
     * @param password    The buyer's password.
     * @param firstName   The buyer's first name.
     * @param lastName    The buyer's last name.
     * @param email       The buyer's email.
     * @param phoneNumber The buyer's phone number.
     * @param address     The buyer's address.
     * @return An {@code OperationResult} indicating the success or failure of the registration.
     */
    public OperationResult registerNewBuyer(String username, String password, String firstName, String lastName, String email, String phoneNumber, String address) {
        // validate parameters ?

        db.add(DataMap.BUYERS, new Buyer(username, password.hashCode(), firstName, lastName, email, phoneNumber, address, 0));

        return new OperationResult(true, "");
    }

    /**
     * Registers a new seller with the specified information.
     *
     * @param name        The seller's name.
     * @param password    The seller's password.
     * @param email       The seller's email.
     * @param phoneNumber The seller's phone number.
     * @param address     The seller's address.
     * @return An {@code OperationResult} indicating the success or failure of the registration.
     */
    public OperationResult registerNewSeller(String name, String password, String email, String phoneNumber, String address) {
        // validate parameters ?

        db.add(DataMap.SELLERS, new Seller(name, password.hashCode(), email, phoneNumber, address));

        return new OperationResult(true, "");
    }

    /**
     * Checks if a buyer username is available.
     *
     * @param username The buyer's username to check.
     * @return {@code true} if the username is available, {@code false} otherwise.
     */
    public boolean isBuyerNameAvailable(String username) {
        return db.<Buyer>get(DataMap.BUYERS, (buyer) -> buyer.getUsername().equalsIgnoreCase(username)).size() == 0;
    }

    /**
     * Checks if a seller name is available.
     *
     * @param name The seller's name to check.
     * @return {@code true} if the name is available, {@code false} otherwise.
     */
    public boolean isSellerNameAvailable(String name) {
        return db.<Seller>get(DataMap.SELLERS, (seller) -> seller.getName().equalsIgnoreCase(name)).size() == 0;
    }

    /**
     * Checks if the provided password is correct for the specified user.
     *
     * @param userId   The unique identifier of the user.
     * @param password The password to check.
     * @return {@code true} if the password is correct, {@code false} otherwise.
     */
    public boolean isCorrectPassword(UUID userId, String password) {
        User user = db.get(DataMap.BUYERS, userId);
        if (user != null) {
            return user.getPasswordHash() == password.hashCode();
        }

        user = db.get(DataMap.SELLERS, userId);
        if (user != null) {
            return user.getPasswordHash() == password.hashCode();
        }

        return false;
    }
}
