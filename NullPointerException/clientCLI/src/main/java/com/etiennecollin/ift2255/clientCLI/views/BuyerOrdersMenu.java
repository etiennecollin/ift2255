/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.*;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class BuyerOrdersMenu extends View {
    private final ShopController shopController;
    private final ProfileController profileController;

    public BuyerOrdersMenu(ShopController shopController, ProfileController profileController) {
        this.shopController = shopController;
        this.profileController = profileController;
    }

    @Override
    public void render() {
        clearConsole();
        List<Order> orders = shopController.getBuyerOrders();

        if (orders.isEmpty()) {
            System.out.println(prettify("No orders"));
            waitForKey();
            return;
        }

        prettyPaginationMenu(orders, 3, "Display order", order -> {
            System.out.println(prettify("--------------------"));
            System.out.println(prettify("Order date: " + order.getOrderDate()));
            System.out.println(prettify("State: " + order.getState()));
            System.out.println(prettify("Cost: " + Utils.formatMoney(order.getTotalCost())));
            System.out.println(prettify("Fidelity points earned: " + order.getFidelityPointsEarned()));
            System.out.println(prettify("Number of products: " + order.getProducts().size()));
        }, order -> "Order of " + order.getOrderDate(), this::displayBuyerOrderActions);
    }

    private void displayBuyerOrderActions(Order order) {
        ArrayList<DynamicMenuItem> options = new ArrayList<>();

        options.add(new DynamicMenuItem("Review a product", () -> {
            prettyPaginationMenu(order.getProducts(), 3, "Review product", (tuple) -> {
                Product product = tuple.first;
                int quantity = tuple.second;
                String totalPrice = Utils.formatMoney((product.getPrice() - product.getPromoDiscount()) * quantity);

                System.out.println(prettify("--------------------"));
                System.out.println(prettify("Product name: " + product.getTitle()));
                System.out.println(prettify("Quantity: " + quantity));
                System.out.println(prettify("Unit price: " + Utils.formatMoney(product.getPrice() - product.getPromoDiscount())));
                System.out.println(prettify("Total price: " + totalPrice));
            }, tuple -> tuple.first.getTitle(), tuple -> shopController.displayProductReview(tuple.first.getId()));
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
                // TODO reimplement create ticket
//                createTicket(order);
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> !order.getState().equals(OrderState.Cancelled) && LocalDate.now().isBefore(order.getOrderDate().plusDays(365))));
        options.add(new DynamicMenuItem("Return items", () -> {
            if (prettyPromptBool("Do you really want to return items from this order?")) {
                // TODO reimplement return menu
//                displayReturnMenu(order);
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> !order.getState().equals(OrderState.Cancelled) && (order.getShipment() == null || LocalDate.now().isBefore(order.getShipment().getExpectedDeliveryDate().plusDays(30)))));
        options.add(new DynamicMenuItem("Exchange items", () -> {
            if (prettyPromptBool("Do you really want to exchange items from this order?")) {
                // TODO reimplement exchange menu
//                displayExchangeMenu(order);
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> !order.getState().equals(OrderState.Cancelled) && (order.getShipment() == null || LocalDate.now().isBefore(order.getShipment().getExpectedDeliveryDate().plusDays(30)))));
        options.add(new DynamicMenuItem("Cancel order", () -> {
            if (prettyPromptBool("Do you really want to cancel this order?")) {
                OperationResult result = shopController.cancelOrder(order.getId());
                System.out.println(prettify(result.message()));
            } else {
                System.out.println(prettify("Action cancelled"));
            }
        }, () -> order.getState().equals(OrderState.InProduction)));

        prettyDynamicMenu("Select action", "Go back", options, () -> displayOrder(order));
    }

    private void displayOrder(Order order) {
        clearConsole();
        System.out.println(prettify("Order date: " + order.getOrderDate()));
        System.out.println(prettify("State: " + order.getState()));
        System.out.println(prettify("Cost: " + Utils.formatMoney(order.getTotalCost())));
        System.out.println(prettify("Fidelity points earned: " + order.getFidelityPointsEarned()));
        System.out.println(prettify("Number of products: " + order.getProducts().size()));
        Seller seller = profileController.getSeller(order.getSellerId());
        System.out.println(prettify("Seller: " + seller.getName()));
        System.out.println(prettify("Fidelity points used to pay: " + order.getPayementMethod().getFidelityPointsUsed()));
        System.out.println(prettify("Money used to pay: " + Utils.formatMoney(order.getPayementMethod().getMoneyUsed())));
        System.out.println(prettify("Shipping Address: " + order.getAddress()));
        if (order.getState().equals(OrderState.InTransit)) {
            System.out.println(prettify("Shipping company: " + order.getShipment().getShippingCompany()));
            System.out.println(prettify("Delivery date: " + order.getShipment().getExpectedDeliveryDate()));
            System.out.println(prettify("Tracking number: " + order.getShipment().getTrackingNumber()));
        }
        waitForKey();
    }

}
