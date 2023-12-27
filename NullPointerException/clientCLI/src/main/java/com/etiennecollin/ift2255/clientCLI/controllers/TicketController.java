/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.models.Session;
import com.etiennecollin.ift2255.clientCLI.models.TicketingModel;
import com.etiennecollin.ift2255.clientCLI.models.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.models.data.UserType;
import com.etiennecollin.ift2255.clientCLI.views.TicketDisplay;
import com.etiennecollin.ift2255.clientCLI.views.TicketsMenu;
import com.etiennecollin.ift2255.clientCLI.views.ViewRenderer;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Controller class responsible for handling operations related to tickets.
 */
public class TicketController {
    /**
     * The view renderer to display views.
     */
    private final ViewRenderer renderer;
    /**
     * The ticketing models to interact with ticket data.
     */
    private final TicketingModel ticketModel;

    /**
     * Constructs a new TicketController.
     *
     * @param renderer    The view renderer to display views.
     * @param ticketModel The ticketing models to interact with ticket data.
     */
    public TicketController(ViewRenderer renderer, TicketingModel ticketModel) {
        this.renderer = renderer;
        this.ticketModel = ticketModel;
    }

    /**
     * Displays the details of a specific ticket.
     *
     * @param ticketId The UUID of the ticket to display.
     */
    public void displayTicket(UUID ticketId) {
        renderer.addNextView(new TicketDisplay(ticketId, this, UniShop.getInstance().getProfileController()), true);
    }

    /**
     * Displays a menu with a list of tickets.
     */
    public void displayTickets() {
        renderer.addNextView(new TicketsMenu(this, UniShop.getInstance().getProfileController()), true);
    }

    /**
     * Retrieves a specific ticket by its UUID.
     *
     * @param ticketId The UUID of the ticket to retrieve.
     *
     * @return The ticket with the specified UUID.
     */
    public Ticket getTicket(UUID ticketId) {
        return ticketModel.getTicket(ticketId);
    }

    /**
     * Retrieves a list of tickets based on the user's role (Buyer or Seller).
     *
     * @return A list of tickets associated with the user.
     */
    public List<Ticket> getTickets() {
        UUID userId = Session.getInstance().getUserId();

        if (Session.getInstance().getUserType() == UserType.Buyer) {
            return ticketModel.getTickets((ticket) -> ticket.getBuyerId() == userId);
        } else {
            return ticketModel.getTickets((ticket) -> ticket.getSellerId() == userId);
        }
    }

    /**
     * Creates a return shipment for a ticket.
     *
     * @param ticketId        The UUID of the ticket for which to create a return shipment.
     * @param trackingNumber  The tracking number of the return shipment.
     * @param deliveryDate    The delivery date of the return shipment.
     * @param shippingCompany The shipping company used for the return shipment.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult createReturnShipment(UUID ticketId, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        return ticketModel.createReturnShipment(ticketId, trackingNumber, deliveryDate, shippingCompany);
    }

    /**
     * Creates a replacement shipment for a ticket.
     *
     * @param ticketId        The UUID of the ticket for which to create a replacement shipment.
     * @param trackingNumber  The tracking number of the replacement shipment.
     * @param deliveryDate    The delivery date of the replacement shipment.
     * @param shippingCompany The shipping company used for the replacement shipment.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult createReplacementShipment(UUID ticketId, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        return ticketModel.createReplacementShipment(ticketId, trackingNumber, deliveryDate, shippingCompany);
    }
}
