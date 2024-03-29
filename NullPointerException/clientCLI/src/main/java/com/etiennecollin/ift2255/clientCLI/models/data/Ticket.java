/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a ticket for a product issue or return in the system.
 */
public class Ticket extends DatabaseObject {
    /**
     * The buyer's ID associated with the ticket.
     */
    private final UUID buyerId;
    /**
     * The seller's ID associated with the ticket.
     */
    private final UUID sellerId;
    /**
     * The date when the ticket was created.
     */
    private final LocalDate creationDate;
    /**
     * The suggested solution for the ticket.
     */
    private String suggestedSolution;
    /**
     * The return shipment associated with the ticket.
     */
    private Shipment returnShipment;
    /**
     * The list of products and their quantities associated with the ticket.
     */
    private ArrayList<Tuple<Product, Integer>> products;
    /**
     * The order ID associated with the ticket.
     */
    private UUID orderId;
    /**
     * The order ID associated with the ticket.
     */
    private UUID replacementOrderId;
    /**
     * The description of the problem reported in the ticket.
     */
    private String problemDescription;
    /**
     * The description of the replacement product.
     */
    private String replacementProductDescription;
    /**
     * The current state of the ticket.
     */
    private TicketState state;
    /**
     * The cause of the ticket.
     */
    private TicketCause cause;

    /**
     * Constructs a Ticket object with the given parameters.
     *
     * @param problemDescription The description of the problem reported in the ticket.
     * @param orderId            The order ID associated with the ticket.
     * @param products           The list of products and their quantities associated with the ticket.
     * @param cause              The cause of the ticket.
     * @param state              The current state of the ticket.
     * @param buyerId            The buyer's ID associated with the ticket.
     * @param sellerId           The seller's ID associated with the ticket.
     */
    public Ticket(String problemDescription, UUID orderId, ArrayList<Tuple<Product, Integer>> products, TicketCause cause, TicketState state, UUID buyerId, UUID sellerId) {
        this.products = products;
        this.orderId = orderId;
        this.cause = cause;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.problemDescription = problemDescription;
        this.suggestedSolution = "";
        this.returnShipment = null;
        this.state = state;
        this.creationDate = LocalDate.now();
    }

    /**
     * Gets the cause of the ticket.
     *
     * @return The cause of the ticket.
     */
    public TicketCause getCause() {
        return cause;
    }

    /**
     * Sets the cause of the ticket.
     *
     * @param cause The cause of the ticket.
     */
    public void setCause(TicketCause cause) {
        this.cause = cause;
    }

    /**
     * Compares this Ticket object to another object for equality.
     *
     * @param o The object to compare.
     *
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(getId(), ticket.getId());
    }

    /**
     * Gets the return shipment associated with the ticket.
     *
     * @return The return shipment associated with the ticket.
     */
    public Shipment getReturnShipment() {
        return returnShipment;
    }

    /**
     * Sets the return shipment associated with the ticket.
     *
     * @param returnShipment The return shipment associated with the ticket.
     */
    public void setReturnShipment(Shipment returnShipment) {
        this.returnShipment = returnShipment;
    }

    /**
     * Gets the creation date of the ticket.
     *
     * @return The creation date of the ticket.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the order ID associated with the ticket.
     *
     * @return The order ID associated with the ticket.
     */
    public UUID getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID associated with the ticket.
     *
     * @param orderId The order ID associated with the ticket.
     */
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the replacement order ID associated with the ticket.
     *
     * @return The replacement order ID associated with the ticket.
     */
    public UUID getReplacementOrderId() {
        return replacementOrderId;
    }

    /**
     * Sets the replacement order ID associated with the ticket.
     *
     * @param replacementOrderId The replacement order ID associated with the ticket.
     */
    public void setReplacementOrderId(UUID replacementOrderId) {
        this.replacementOrderId = replacementOrderId;
    }

    /**
     * Gets the current state of the ticket.
     *
     * @return The current state of the ticket.
     */
    public TicketState getState() {
        return state;
    }

    /**
     * Sets the current state of the ticket.
     *
     * @param state The current state of the ticket.
     */
    public void setState(TicketState state) {
        this.state = state;
    }

    /**
     * Gets the list of products and their quantities associated with the ticket.
     *
     * @return The list of products and their quantities associated with the ticket.
     */
    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    /**
     * Sets the list of products and their quantities associated with the ticket.
     *
     * @param products The list of products and their quantities associated with the ticket.
     */
    public void setProducts(ArrayList<Tuple<Product, Integer>> products) {
        this.products = products;
    }

    /**
     * Gets the buyer's ID associated with the ticket.
     *
     * @return The buyer's ID associated with the ticket.
     */
    public UUID getBuyerId() {
        return buyerId;
    }

    /**
     * Gets the seller's ID associated with the ticket.
     *
     * @return The seller's ID associated with the ticket.
     */
    public UUID getSellerId() {
        return sellerId;
    }

    /**
     * Gets the description of the replacement product.
     *
     * @return The description of the replacement product.
     */
    public String getReplacementProductDescription() {
        return replacementProductDescription;
    }

    /**
     * Sets the description of the replacement product.
     *
     * @param replacementProductDescription The description of the replacement product.
     */
    public void setReplacementProductDescription(String replacementProductDescription) {
        this.replacementProductDescription = replacementProductDescription;
    }

    /**
     * Gets the description of the problem reported in the ticket.
     *
     * @return The description of the problem reported in the ticket.
     */
    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * Sets the description of the problem reported in the ticket.
     *
     * @param problemDescription The description of the problem reported in the ticket.
     */
    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    /**
     * Gets the suggested solution for the ticket.
     *
     * @return The suggested solution for the ticket.
     */
    public String getSuggestedSolution() {
        return suggestedSolution;
    }

    /**
     * Sets the suggested solution for the ticket.
     *
     * @param suggestedSolution The suggested solution for the ticket.
     */
    public void setSuggestedSolution(String suggestedSolution) {
        this.suggestedSolution = suggestedSolution;
    }
}