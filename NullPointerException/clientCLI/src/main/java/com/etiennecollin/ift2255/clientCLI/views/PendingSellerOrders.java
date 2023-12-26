/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.Order;
import com.etiennecollin.ift2255.clientCLI.model.data.OrderState;
import com.etiennecollin.ift2255.clientCLI.model.data.Seller;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.time.LocalDate;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class PendingSellerOrders extends View {
    private final ShopController shopController;
    private final ProfileController profileController;

    public PendingSellerOrders(ShopController shopController, ProfileController profileController) {
        this.shopController = shopController;
        this.profileController = profileController;
    }

    @Override
    public void render() {
        List<Order> orders = shopController.getPendingSellerOrders();

        clearConsole();

        if (orders.isEmpty()) {
            System.out.println(prettify("No orders"));
            waitForKey();
            return;
        }

        prettyPaginationMenu(orders, 2, "Select order to ship", (order) -> {
            System.out.println(prettify("--------------------"));
            String buyerName = profileController.getBuyer(order.getBuyerId()).getUsername();
            System.out.println(prettify("ID: " + order.getId()));
            System.out.println(prettify("Order date: " + order.getOrderDate()));
            System.out.println(prettify("Buyer username: " + buyerName));
            System.out.println(prettify("Address: " + order.getAddress()));
            System.out.println(prettify("State: " + order.getState()));
            System.out.println(prettify("Products:"));
            for (Tuple<Product, Integer> productTuple : order.getProducts()) {
                System.out.println(prettify(productTuple.second + "x " + productTuple.first.getTitle()));
            }
        }, (order) -> "Order #" + order.getId() + " - " + order.getOrderDate(), this::displayOrderShipmentMenu);
    }

    public void displayOrderShipmentMenu(Order order) {
        clearConsole();
        if (order.getState() != OrderState.InProduction) {
            System.out.println(prettify("Order has already been shipped."));
            waitForKey();
            return;
        }
        displayOrder(order);

        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
        String trackingNumber = prettyPrompt("Tracking number", Utils::validateNotEmpty);
        LocalDate expectedDeliveryDate = prettyPromptDate("Expected delivery date");

        if (prettyPromptBool("Ship order?")) {
            OperationResult result = shopController.shipOrder(order.getId(), shippingCompany, trackingNumber, expectedDeliveryDate);
            System.out.println(result.message());
        } else {
            System.out.println("Order status change cancelled");
        }
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
