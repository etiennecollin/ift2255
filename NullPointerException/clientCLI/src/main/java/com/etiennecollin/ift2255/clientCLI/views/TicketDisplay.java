/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.model.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.model.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.model.data.TicketState;

import java.time.LocalDate;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The TicketDisplay class represents a view for displaying and performing actions on a specific ticket.
 * It allows buyers and sellers to interact with and manage the details of a ticket, including creating return
 * shipments, confirming the reception of replacement shipments, setting suggested solutions, and more.
 * <p>
 * The class extends the {@link View} class.
 */
public class TicketDisplay extends View {
    /**
     * The TicketController instance for handling ticket-related functionalities.
     */
    private final TicketController ticketController;
    /**
     * The ProfileController instance for interacting with profile-related functionalities.
     */
    private final ProfileController profileController;
    /**
     * The unique identifier (UUID) of the ticket associated with this TicketDisplay instance.
     */
    private final UUID ticketId;

    /**
     * Constructs a TicketDisplay with the specified ticket ID, TicketController, and ProfileController.
     *
     * @param ticketId          the ID of the ticket to be displayed.
     * @param ticketController  the TicketController used for ticket-related functionalities.
     * @param profileController the ProfileController used for interacting with profile-related functionalities.
     */
    public TicketDisplay(UUID ticketId, TicketController ticketController, ProfileController profileController) {
        this.ticketId = ticketId;
        this.ticketController = ticketController;
        this.profileController = profileController;
    }

    /**
     * Renders the TicketDisplay view, allowing users (buyers and sellers) to interact with and manage a specific ticket.
     * Overrides the render method in the View class.
     */
    @Override
    public void render() {
        Ticket ticket = ticketController.getTicket(ticketId);
        if (ticket == null) {
            return;
        }

        Buyer buyer = profileController.getBuyer();
        if (buyer != null) {
            String[] options = {"Go back", "Create return shipment", "Confirm reception of replacement shipment"};

            loop:
            while (true) {
                clearConsole();
                displayTicket(ticket);

                // Setup action menu
                int answer = prettyMenu("Select action", options);
                switch (answer) {
                    case 0 -> {
                        break loop;
                    }
                    case 1 -> {
                        String trackingNumber = prettyPrompt("Tracking number of return shipment", Utils::validateNotEmpty);
                        LocalDate deliveryDate = prettyPromptDate("Expected delivery date");
                        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);

                        OperationResult result = ticketController.createReturnShipment(ticketId, trackingNumber, deliveryDate, shippingCompany);
                        System.out.println(prettify(result.message()));
                    }
                    case 2 -> {
                        boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the replacement shipment");
                        if (confirmation) {
                            ticket.getReplacementShipment().confirmDelivery();
                            ticket.updateState();
                        } else {
                            System.out.println(prettify("Action cancelled"));
                            waitForKey();
                        }
                    }
                }
            }
        } else { // the user is a seller
            String[] options = {"Go back", "Set suggested solution", "Confirm reception of return shipment", "Set replacement product description", "Create replacement shipment"};

            loop:
            while (true) {
                clearConsole();
                displayTicket(ticket);

                // Setup action menu
                int answer = prettyMenu("Select action", options);
                switch (answer) {
                    case 0 -> {
                        break loop;
                    }
                    case 1 -> {
                        String suggestedSolution = prettyPrompt("Suggested solution", Utils::validateNotEmpty);
                        ticket.setSuggestedSolution(suggestedSolution);
                    }
                    case 2 -> {
                        boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the return shipment");
                        if (confirmation) {
                            ticket.getReturnShipment().confirmDelivery();
                            ticket.updateState();
                        } else {
                            System.out.println(prettify("Action cancelled"));
                            waitForKey();
                        }
                    }
                    case 3 -> {
                        String replacementProductDescription = prettyPrompt("Replacement product description", Utils::validateNotEmpty);
                        ticket.setReplacementProductDescription(replacementProductDescription);
                    }
                    case 4 -> {
                        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
                        String trackingNumber = prettyPrompt("Tracking number of replacement shipment", Utils::validateNotEmpty);
                        LocalDate expectedDeliveryDate = prettyPromptDate("Expected delivery date");

                        OperationResult result = ticketController.createReplacementShipment(ticketId, trackingNumber, expectedDeliveryDate, shippingCompany);
                        System.out.println(prettify(result.message()));
                    }
                }
            }
        }
    }

    /**
     * Displays detailed information about the specified ticket.
     *
     * @param ticket the ticket to be displayed.
     */
    public void displayTicket(Ticket ticket) {
        clearConsole();
        System.out.println(prettify("Creation date: " + ticket.getCreationDate()));
        System.out.println(prettify("State: " + ticket.getState()));
        System.out.println(prettify("For order placed on: " + ticket.getOrder().getOrderDate()));
        String buyerName = profileController.getBuyer(ticket.getBuyerId()).getUsername();
        System.out.println(prettify("Buyer: " + buyerName));
        String sellerName = profileController.getSeller(ticket.getSellerId()).getName();
        System.out.println(prettify("Seller: " + sellerName));
        System.out.println(prettify("Number of products in ticket: " + ticket.getProducts().size()));
        System.out.println(prettify("Cause of ticket: " + ticket.getCause()));
        System.out.println(prettify("Problem description: " + ticket.getProblemDescription()));
        System.out.println(prettify("Suggested solution: " + ticket.getSuggestedSolution()));
        System.out.println(prettify("Replacement product description: " + ticket.getReplacementProductDescription()));

        if (ticket.getState().equals(TicketState.ReturnInTransit)) {
            System.out.println(prettify("Return shipment creation date: " + ticket.getReturnShipment().getCreationDate()));
            System.out.println(prettify("Return shipment tracking number: " + ticket.getReturnShipment().getTrackingNumber()));
        } else if (ticket.getState().equals(TicketState.ReplacementInTransit)) {
            System.out.println(prettify("Replacement shipment creation date: " + ticket.getReplacementShipment().getCreationDate()));
            System.out.println(prettify("Replacement shipment tracking number: " + ticket.getReplacementShipment().getTrackingNumber()));
        }
        waitForKey();
    }
}
