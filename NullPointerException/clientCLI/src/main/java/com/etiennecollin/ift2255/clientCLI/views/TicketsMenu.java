/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.model.data.Ticket;

import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The TicketsMenu class represents a view for displaying and interacting with user tickets.
 * It extends the View class.
 * <p>
 * The class extends the {@link View} class.
 */
public class TicketsMenu extends View {
    /**
     * The ticketController field represents the TicketController used for handling ticket-related logic.
     */
    private final TicketController ticketController;
    /**
     * The profileController field represents the ProfileController used for managing user profiles in UniShop.
     */
    private final ProfileController profileController;

    /**
     * Constructs a new TicketsMenu instance with the specified TicketController and ProfileController.
     *
     * @param ticketController  The TicketController for handling ticket-related logic.
     * @param profileController The ProfileController for managing user profiles in UniShop.
     */
    public TicketsMenu(TicketController ticketController, ProfileController profileController) {
        this.ticketController = ticketController;
        this.profileController = profileController;
    }

    /**
     * Renders the TicketsMenu view, displaying ticket information and providing interaction options.
     * Overrides the render method in the View class.
     */
    @Override
    public void render() {
        List<Ticket> tickets = ticketController.getTickets();
        clearConsole();

        if (tickets.isEmpty()) {
            System.out.println(prettify("No tickets"));
            waitForKey();
        } else {
            prettyPaginationMenu(tickets, 3, "Display ticket", (ticket) -> {
                System.out.println(prettify("--------------------"));
                System.out.println(prettify("Creation date: " + ticket.getCreationDate()));
                System.out.println(prettify("State: " + ticket.getState()));
                System.out.println(prettify("For order placed on: " + ticket.getOrder().getOrderDate()));

                String buyerName = profileController.getBuyer(ticket.getBuyerId()).getUsername();
                System.out.println(prettify("Buyer: " + buyerName));

                String sellerName = profileController.getSeller(ticket.getSellerId()).getName();
                System.out.println(prettify("Seller: " + sellerName));

                System.out.println(prettify("Number of products in ticket: " + ticket.getProducts().size()));
            }, (ticket) -> "Ticket of " + ticket.getCreationDate(), (ticket) -> ticketController.displayTicket(ticket.getId()));
        }
    }
}
