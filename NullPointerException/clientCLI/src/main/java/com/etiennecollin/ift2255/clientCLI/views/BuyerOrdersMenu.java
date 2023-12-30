/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.models.data.Order;
import com.etiennecollin.ift2255.clientCLI.models.data.OrderState;
import com.etiennecollin.ift2255.clientCLI.models.data.Seller;
import com.etiennecollin.ift2255.clientCLI.models.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The BuyerOrdersMenu class represents the view for displaying and interacting with a buyer's order history
 * and details in the client CLI application. Users can view order information, review products, confirm reception,
 * report issues, return items, exchange items, and cancel orders.
 * <p>
 * The class extends the {@link View} class.
 */
public class BuyerOrdersMenu extends View {
    /**
     * The ShopController used for interacting with shop-related actions and operations.
     */
    private final ShopController shopController;
    /**
     * The ProfileController used for interacting with buyer profiles and related actions.
     */
    private final ProfileController profileController;
    /**
     * The ticketController field represents the TicketController used for handling ticket-related logic.
     */
    private final TicketController ticketController;

    /**
     * Constructs a BuyerOrdersMenu view with the specified ShopController and ProfileController.
     *
     * @param shopController    the ShopController used for interacting with shop-related actions and operations.
     * @param profileController the ProfileController used for interacting with buyer profiles and related actions.
     * @param ticketController  the TicketController for handling ticket-related logic.
     */
    public BuyerOrdersMenu(ShopController shopController, ProfileController profileController, TicketController ticketController) {
        this.shopController = shopController;
        this.profileController = profileController;
        this.ticketController = ticketController;
    }

    /**
     * Renders the BuyerOrdersMenu view, allowing the user to view their order history and perform various actions on each order.
     */
    @Override
    public void render() {
        clearConsole();
        AtomicReference<List<Order>> orders = new AtomicReference<>(shopController.getBuyerOrders());

        if (orders.get().isEmpty()) {
            System.out.println(prettify("No orders"));
            waitForKey();
            return;
        }

        prettyPaginationMenu(orders.get(), 3, "Display order",
                order -> {
                    System.out.println(prettify("--------------------"));
                    System.out.println(prettify("Order date: " + order.getOrderDate()));
                    System.out.println(prettify("State: " + order.getState()));
                    System.out.println(prettify("Cost: " + Utils.formatMoney(order.getTotalCost())));
                    System.out.println(prettify("Fidelity points earned: " + order.getFidelityPointsEarned()));
                    System.out.println(prettify("Number of products: " + order.getProducts().size()));
                }, order -> "Order of " + order.getOrderDate(),
                order -> {
                    displayBuyerOrderActions(order);
                    return false;
                }
        );
    }

    /**
     * Displays a menu with dynamic options for the actions that can be performed on a specific buyer's order.
     *
     * @param order The Order object representing the buyer's order.
     */
    private void displayBuyerOrderActions(Order order) {
        ArrayList<DynamicMenuItem> options = new ArrayList<>();

        Ticket existingTicket = ticketController.getTicketForOrder(order.getId());
        boolean orderTicketExists = existingTicket != null;

        options.add(new DynamicMenuItem("Review a product", () -> {
            prettyPaginationMenu(order.getProducts(), 3, "Review product",
                    (tuple) -> {
                        Product product = tuple.first;
                        int quantity = tuple.second;
                        String totalPrice = Utils.formatMoney((product.getPrice() - product.getPromoDiscount()) * quantity);

                        System.out.println(prettify("--------------------"));
                        System.out.println(prettify("Product name: " + product.getTitle()));
                        System.out.println(prettify("Quantity: " + quantity));
                        System.out.println(prettify("Unit price: " + Utils.formatMoney(product.getPrice() - product.getPromoDiscount())));
                        System.out.println(prettify("Total price: " + totalPrice));
                    }, tuple -> tuple.first.getTitle(),
                    tuple -> {
                        shopController.displayProductReview(tuple.first.getId());
                        return false;
                    }
            );
        }, () -> order.getState().equals(OrderState.Delivered)));
        options.add(new DynamicMenuItem("Confirm reception of order", () -> {
            if (prettyPromptBool("Do you really want to mark this order as delivered?")) {
                OperationResult result = shopController.confirmDelivery(order.getId());
                System.out.println(prettify(result.message()));
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> order.getState().equals(OrderState.InTransit)));
        options.add(new DynamicMenuItem("Report issue with order", () -> {
            if (prettyPromptBool("Do you really want to open a ticket for this order?")) {
                ticketController.displayTicketCreation(order.getId());
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> !orderTicketExists && !order.getState().equals(OrderState.Cancelled) && LocalDate.now().isBefore(order.getOrderDate().plusDays(365))));
        options.add(new DynamicMenuItem("Return items", () -> {
            if (prettyPromptBool("Do you really want to return items from this order?")) {
                ticketController.displayProductReturnCreation(order.getId());
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> !orderTicketExists && !order.getState().equals(OrderState.Cancelled) && (order.getShipment() == null || LocalDate.now().isBefore(order.getShipment().getReceptionDate().plusDays(30)))));
        options.add(new DynamicMenuItem("Exchange items", () -> {
            if (prettyPromptBool("Do you really want to exchange items from this order?")) {
                ticketController.displayProductExchangeCreation(order.getId());
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> !orderTicketExists && !order.getState().equals(OrderState.Cancelled) && (order.getShipment() == null || LocalDate.now().isBefore(order.getShipment().getReceptionDate().plusDays(30)))));
        options.add(new DynamicMenuItem("Cancel order", () -> {
            if (prettyPromptBool("Do you really want to cancel this order?")) {
                OperationResult result = shopController.cancelOrder(order.getId());
                System.out.println(prettify(result.message()));
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> !orderTicketExists && order.getState().equals(OrderState.InProduction)));
        options.add(new DynamicMenuItem("View attached ticket", () -> {
            if (existingTicket != null) {
                ticketController.displayTicket(existingTicket.getId());
            }
        }, () -> orderTicketExists));

        prettyDynamicMenu("Select action", "Go back", options, () -> displayOrder(order));
    }

    /**
     * Displays detailed information about a specific buyer's order.
     *
     * @param order The Order object representing the buyer's order.
     */
    private void displayOrder(Order order) {
        clearConsole();
        System.out.println(prettify("Order date: " + order.getOrderDate()));
        System.out.println(prettify("State: " + order.getState()));
        System.out.println(prettify("Cost: " + Utils.formatMoney(order.getTotalCost())));
        System.out.println(prettify("Fidelity points earned: " + order.getFidelityPointsEarned()));
        System.out.println(prettify("Number of products: " + order.getProducts().size()));
        Seller seller = profileController.getSeller(order.getSellerId());
        System.out.println(prettify("Seller: " + seller.getName()));
        System.out.println(prettify("Fidelity points used to pay: " + order.getPaymentMethod().getFidelityPointsUsed()));
        System.out.println(prettify("Money used to pay: " + Utils.formatMoney(order.getPaymentMethod().getMoneyUsed())));
        System.out.println(prettify("Shipping Address: " + order.getAddress()));
        if (order.getState().equals(OrderState.InTransit)) {
            System.out.println(prettify("Shipping company: " + order.getShipment().getShippingCompany()));
            System.out.println(prettify("Delivery date: " + order.getShipment().getExpectedDeliveryDate()));
            System.out.println(prettify("Tracking number: " + order.getShipment().getTrackingNumber()));
        }
        waitForKey();
    }
}
