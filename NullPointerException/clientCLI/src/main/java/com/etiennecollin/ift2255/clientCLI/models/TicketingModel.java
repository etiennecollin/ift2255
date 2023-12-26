/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.models.data.DataMap;
import com.etiennecollin.ift2255.clientCLI.models.data.Database;
import com.etiennecollin.ift2255.clientCLI.models.data.Shipment;
import com.etiennecollin.ift2255.clientCLI.models.data.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The {@code TicketingModel} class represents the models for handling ticket-related operations in the CLI application.
 * It interacts with the underlying database to perform operations such as retrieving tickets, creating return shipments,
 * and creating replacement shipments.
 */
public class TicketingModel {
    /**
     * The underlying database used by the models.
     */
    private final Database db;

    /**
     * Constructs a new {@code TicketingModel} with the specified database.
     *
     * @param db The database used by the models to store ticket-related data.
     */
    public TicketingModel(Database db) {
        this.db = db;
    }

    /**
     * Retrieves a list of tickets that satisfy the given predicate.
     *
     * @param predicate The predicate used to filter tickets.
     * @return A list of tickets that satisfy the given predicate.
     */
    public List<Ticket> getTickets(Predicate<Ticket> predicate) {
        return db.get(DataMap.TICKETS, predicate);
    }

    /**
     * Creates a return shipment for the specified ticket with the given information.
     *
     * @param ticketId        The unique identifier of the ticket.
     * @param trackingNumber  The tracking number of the return shipment.
     * @param deliveryDate    The expected delivery date of the return shipment.
     * @param shippingCompany The shipping company responsible for the return shipment.
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createReturnShipment(UUID ticketId, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        Ticket ticket = getTicket(ticketId);
        if (ticket == null) {
            return new OperationResult(false, "Ticket does not exist.");
        }

        if (ticket.getReturnShipment() != null) {
            return new OperationResult(false, "Ticket already has a return shipment.");
        }

        boolean result = db.<Ticket>update(DataMap.TICKETS, (t) -> t.setReturnShipment(new Shipment(trackingNumber, deliveryDate, shippingCompany)), ticketId);
        if (result) {
            return new OperationResult(true, "Return shipment information added.");
        } else {
            return new OperationResult(false, "Unable to update ticket.");
        }
    }

    /**
     * Retrieves the ticket with the specified ID from the database.
     *
     * @param ticketId The unique identifier of the ticket.
     * @return The ticket with the specified ID, or {@code null} if the ticket does not exist.
     */
    public Ticket getTicket(UUID ticketId) {
        return db.get(DataMap.TICKETS, ticketId);
    }

    /**
     * Creates a replacement shipment for the specified ticket with the given information.
     *
     * @param ticketId        The unique identifier of the ticket.
     * @param trackingNumber  The tracking number of the replacement shipment.
     * @param deliveryDate    The expected delivery date of the replacement shipment.
     * @param shippingCompany The shipping company responsible for the replacement shipment.
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createReplacementShipment(UUID ticketId, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        Ticket ticket = getTicket(ticketId);
        if (ticket == null) {
            return new OperationResult(false, "Ticket does not exist.");
        }

        if (ticket.getReplacementShipment() != null) {
            return new OperationResult(false, "Ticket already has a replacement shipment.");
        }

        boolean result = db.<Ticket>update(DataMap.TICKETS, (t) -> t.setReplacementShipment(new Shipment(trackingNumber, deliveryDate, shippingCompany)), ticketId);
        if (result) {
            return new OperationResult(true, "Replacement shipment information added.");
        } else {
            return new OperationResult(false, "Unable to update ticket.");
        }
    }
}
