/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.model.data.Ticket;

import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class TicketsMenu extends View {
    private final TicketController ticketController;
    private final ProfileController profileController;

    public TicketsMenu(TicketController ticketController, ProfileController profileController) {
        this.ticketController = ticketController;
        this.profileController = profileController;
    }

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
