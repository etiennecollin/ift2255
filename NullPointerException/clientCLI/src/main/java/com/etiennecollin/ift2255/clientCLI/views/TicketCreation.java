/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.models.data.Order;
import com.etiennecollin.ift2255.clientCLI.models.data.TicketCause;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The TicketCreation class represents a view for creating a ticket in the client CLI.
 * Users can select the type of issue, provide a description, and confirm or cancel the ticket creation process.
 */
public class TicketCreation extends View {
    /**
     * The controller responsible for handling tickets.
     */
    private final TicketController ticketController;
    /**
     * The controller responsible for handling shop-related operations.
     */
    private final ShopController shopController;
    /**
     * The ID of the order associated with the ticket.
     */
    private final UUID orderId;

    /**
     * Constructs a new TicketCreation view with the specified order ID and ticket controller.
     *
     * @param orderId          The ID of the order associated with the ticket.
     * @param ticketController The controller responsible for handling tickets.
     */
    public TicketCreation(UUID orderId, TicketController ticketController, ShopController shopController) {
        this.orderId = orderId;
        this.ticketController = ticketController;
        this.shopController = shopController;
    }

    /**
     * Renders the ticket creation menu, allowing users to select the type of issue,
     * provide a description, and confirm or cancel the ticket creation process.
     */
    @Override
    public void render() {
        Order order = shopController.getOrder(orderId);

        HashSet<Tuple<Product, Integer>> returnProducts = new HashSet<>();
        prettyPaginationMenu(order.getProducts(), 5, "Select item with problem",
                productTuple -> System.out.println(prettify(productTuple.first + " x" + productTuple.second)),
                productTuple -> productTuple.first + " x" + productTuple.second,
                productTuple ->  {
                    returnProducts.add(productTuple);
                    return true;
                }
        );

        if (returnProducts.isEmpty()) {
            System.out.println(prettify("No products selected to return"));
            waitForKey();
            return;
        }

        TicketCause cause = prettyMenu("Select the type of issue", TicketCause.class);
        String description = prettyPrompt("Description of problem", Utils::validateNotEmpty);

        if (prettyPromptBool("Do you really want to open a ticket for this order?")) {
            OperationResult result = ticketController.createManualTicket(orderId, new ArrayList<>(returnProducts), description, cause);

            System.out.println(prettify(result.message()));
        } else {
            System.out.println(prettify("Cancelled ticket creation"));
        }

        waitForKey();
    }
}
