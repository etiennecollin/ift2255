/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models;

import com.etiennecollin.ift2255.clientCLI.models.data.Order;
import com.etiennecollin.ift2255.clientCLI.models.data.SessionCartDatabase;
import com.etiennecollin.ift2255.clientCLI.models.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.models.data.UserType;

import java.util.UUID;

/**
 * The {@code Session} class represents a user session in the system.
 * It stores information about the user, such as the user ID and user type.
 * The session is a singleton, and it can be created, accessed, and cleared during the application's lifecycle.
 */
public class Session {
    /**
     * The singleton instance of the user session.
     */
    private static Session _instance;
    /**
     * The unique identifier of the user associated with the session.
     */
    private final UUID userId;
    /**
     * The type of the user associated with the session (e.g., Buyer, Seller).
     */
    private final UserType userType;
    /**
     * Flag indicating whether the session is in an exchange process.
     * Default value is {@code false}.
     */
    private boolean isInExchangeProcess = false;
    /**
     * The exchange ticket associated with the session.
     */
    private Ticket exchangeTicket;
    /**
     * The exchange cart associated with the session.
     */
    private SessionCartDatabase exchangeCart;
    /**
     * The exchange order associated with the session.
     */
    private Order exchangeOrder;

    /**
     * Private constructor to create a new user session.
     *
     * @param userId   The unique identifier of the user.
     * @param userType The type of the user (e.g., Buyer, Seller).
     */
    private Session(UUID userId, UserType userType) {
        this.userId = userId;
        this.userType = userType;
    }

    /**
     * Gets the instance of the user session.
     *
     * @return The instance of the user session.
     */
    public static Session getInstance() {
        return _instance;
    }

    /**
     * Creates a new user session with the specified user ID and user type.
     *
     * @param userId   The unique identifier of the user.
     * @param userType The type of the user (e.g., Buyer, Seller).
     *
     * @return The newly created user session.
     */
    public static Session createSession(UUID userId, UserType userType) {
        _instance = new Session(userId, userType);
        return _instance;
    }

    /**
     * Clears the current user session.
     */
    public static void clearSession() {
        _instance = null;
    }

    /**
     * Gets the unique identifier of the user associated with the session.
     *
     * @return The user ID.
     */
    public UUID getUserId() {
        return this.userId;
    }

    /**
     * Gets the type of the user associated with the session.
     *
     * @return The user type (e.g., Buyer, Seller).
     */
    public UserType getUserType() {
        return this.userType;
    }

    /**
     * Gets the status indicating whether the session is in an exchange process.
     *
     * @return {@code true} if the session is in an exchange process; {@code false} otherwise.
     */
    public boolean getIsInExchangeProcess() {
        return isInExchangeProcess;
    }

    /**
     * Sets the status indicating whether the session is in an exchange process.
     *
     * @param inExchangeProcess {@code true} if the session is in an exchange process; {@code false} otherwise.
     */
    public void setInExchangeProcess(boolean inExchangeProcess) {
        isInExchangeProcess = inExchangeProcess;
    }

    /**
     * Gets the exchange ticket associated with the session.
     *
     * @return The exchange ticket.
     */
    public Ticket getExchangeTicket() {
        return exchangeTicket;
    }

    /**
     * Sets the exchange ticket associated with the session.
     *
     * @param exchangeTicket The exchange ticket to set.
     */
    public void setExchangeTicket(Ticket exchangeTicket) {
        this.exchangeTicket = exchangeTicket;
    }

    /**
     * Gets the exchange cart associated with the session.
     *
     * @return The exchange cart.
     */
    public SessionCartDatabase getExchangeCart() {
        return exchangeCart;
    }

    /**
     * Creates a new exchange cart for the session.
     */
    public void createExchangeCart() {
        exchangeCart = new SessionCartDatabase();
    }

    /**
     * Deletes the exchange cart associated with the session.
     */
    public void deleteExchangeCart() {
        this.exchangeCart = null;
    }

    /**
     * Gets the exchange order associated with the session.
     *
     * @return The exchange order.
     */
    public Order getExchangeOrder() {
        return exchangeOrder;
    }

    /**
     * Sets the exchange order associated with the session.
     *
     * @param exchangeOrder The exchange order to set.
     */
    public void setExchangeOrder(Order exchangeOrder) {
        this.exchangeOrder = exchangeOrder;
    }
}
