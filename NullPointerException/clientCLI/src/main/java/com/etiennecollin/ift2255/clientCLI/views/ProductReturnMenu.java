/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
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
 * The ProductReturnMenu class represents a view for handling product returns in the client CLI.
 * Users can select items with issues, choose the type of issue, and create a return ticket for the selected products.
 */
public class ProductReturnMenu extends View {
    /**
     * The controller responsible for handling tickets.
     */
    private final TicketController ticketController;
    /**
     * The controller responsible for handling shop-related operations.
     */
    private final ShopController shopController;
    /**
     * The ID of the order associated with the return.
     */
    private final UUID orderId;

    /**
     * Constructs a new ProductReturnMenu with the specified order ID, ticket controller, and shop controller.
     *
     * @param orderId          The ID of the order associated with the return.
     * @param ticketController The controller responsible for handling tickets.
     * @param shopController   The controller responsible for handling shop-related operations.
     */
    public ProductReturnMenu(UUID orderId, TicketController ticketController, ShopController shopController) {
        this.ticketController = ticketController;
        this.shopController = shopController;
        this.orderId = orderId;
    }

    /**
     * Renders the product return menu, allowing users to select items with issues,
     * choose the type of issue, and create a return ticket for the selected products.
     */
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
