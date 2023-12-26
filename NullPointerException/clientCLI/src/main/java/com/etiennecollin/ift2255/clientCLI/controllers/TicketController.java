/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.model.Session;
import com.etiennecollin.ift2255.clientCLI.model.TicketingModel;
import com.etiennecollin.ift2255.clientCLI.model.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.model.data.UserType;
import com.etiennecollin.ift2255.clientCLI.views.TicketDisplay;
import com.etiennecollin.ift2255.clientCLI.views.TicketsMenu;
import com.etiennecollin.ift2255.clientCLI.views.ViewRenderer;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TicketController {
    private final ViewRenderer renderer;
    private final TicketingModel ticketModel;

    public TicketController(ViewRenderer renderer, TicketingModel ticketModel) {
        this.renderer = renderer;
        this.ticketModel = ticketModel;
    }

    public void displayTicket(UUID ticketId) {
        renderer.addNextView(new TicketDisplay(ticketId, this, UniShop.getInstance().getProfileController()), true);
    }

    public void displayTickets() {
        renderer.addNextView(new TicketsMenu(this, UniShop.getInstance().getProfileController()), true);
    }

    public Ticket getTicket(UUID ticketId) {
        return ticketModel.getTicket(ticketId);
    }

    public List<Ticket> getTickets() {
        UUID userId = Session.getInstance().getUserId();

        if (Session.getInstance().getUserType() == UserType.Buyer) {
            return ticketModel.getTickets((ticket) -> ticket.getBuyerId() == userId);
        }
        else {
            return ticketModel.getTickets((ticket) -> ticket.getSellerId() == userId);
        }
    }

    public OperationResult createReturnShipment(UUID ticketId, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        return ticketModel.createReturnShipment(ticketId, trackingNumber, deliveryDate, shippingCompany);
    }

    public OperationResult createReplacementShipment(UUID ticketId, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        return ticketModel.createReplacementShipment(ticketId, trackingNumber, deliveryDate, shippingCompany);
    }
}
