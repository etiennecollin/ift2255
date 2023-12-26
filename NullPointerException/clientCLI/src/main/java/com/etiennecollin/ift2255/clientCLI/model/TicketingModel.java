/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.DataMap;
import com.etiennecollin.ift2255.clientCLI.model.data.Database;
import com.etiennecollin.ift2255.clientCLI.model.data.Shipment;
import com.etiennecollin.ift2255.clientCLI.model.data.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TicketingModel {
    private final Database db;

    public TicketingModel(Database db) {
        this.db = db;
    }

    public List<Ticket> getTickets(Predicate<Ticket> predicate) {
        return db.get(DataMap.TICKETS, predicate);
    }

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

    public Ticket getTicket(UUID ticketId) {
        return db.get(DataMap.TICKETS, ticketId);
    }

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
