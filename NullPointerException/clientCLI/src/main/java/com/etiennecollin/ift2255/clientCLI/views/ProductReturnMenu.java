/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.models.data.Order;
import com.etiennecollin.ift2255.clientCLI.models.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.models.data.TicketCause;
import com.etiennecollin.ift2255.clientCLI.models.data.TicketState;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

// TODO javadoc
public class ProductReturnMenu extends View {
    /**
     * The ticketController field represents the TicketController used for handling ticket-related logic.
     */
    private final TicketController ticketController;
    private final ShopController shopController;
    private final UUID orderId;

    public ProductReturnMenu(UUID orderId, TicketController ticketController, ShopController shopController) {
        this.ticketController = ticketController;
        this.shopController = shopController;
        this.orderId = orderId;
    }

    @Override
    public void render() {
        Order order = shopController.getOrder(orderId);

        HashSet<Tuple<Product, Integer>> returnProducts = new HashSet<>();
        prettyPaginationMenu(order.getProducts(), 5, "Select item with problem", productTuple -> System.out.println(prettify(productTuple.first + " x" + productTuple.second)), productIntegerTuple -> productIntegerTuple.first + " x" + productIntegerTuple.second, returnProducts::add);

        if (returnProducts.isEmpty()) {
            System.out.println(prettify("No products selected to return"));
            waitForKey();
            return;
        }

        TicketCause cause = prettyMenu("Select the type of issue", TicketCause.class);

        OperationResult result = ticketController.createReturnTicket(orderId, new ArrayList<>(returnProducts), cause);
        System.out.println(prettify(result.message()));

        waitForKey();
    }
}
