/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.models.data.Order;
import com.etiennecollin.ift2255.clientCLI.models.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.models.data.TicketState;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
     * The ShopController instance for interacting with shop-related functionalities.
     */
    private final ShopController shopController;
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
     * @param shopController    the ShopController used for shop-related functionalities
     */
    public TicketDisplay(UUID ticketId, TicketController ticketController, ProfileController profileController, ShopController shopController) {
        this.ticketId = ticketId;
        this.ticketController = ticketController;
        this.profileController = profileController;
        this.shopController = shopController;
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
            displayBuyerActions(ticket);
        } else {
            displaySellerActions(ticket);
        }
    }

    /**
     * Displays the available actions for a buyer related to the specified ticket.
     * Allows the buyer to confirm the reception of a replacement shipment.
     *
     * @param ticket The ticket for which actions are displayed.
     */
    public void displayBuyerActions(Ticket ticket) {
        //        String[] options = {"Go back", "Confirm reception of replacement shipment"};

        //        clearConsole();
        //        displayTicket(ticket);

        ArrayList<DynamicMenuItem> options = new ArrayList<>();

        options.add(new DynamicMenuItem("View related orders", () -> {
            List<Order> orderList = new ArrayList<>();
            orderList.add(shopController.getOrder(ticket.getOrderId()));

            if (ticket.getReplacementOrderId() != null) {
                orderList.add(shopController.getOrder(ticket.getReplacementOrderId()));
                System.out.println(prettify("Problem order is first. Replacement order is second."));
                waitForKey();
            }
            shopController.displayBuyerOrdersMenu(orderList);
        }, () -> true));
        options.add(new DynamicMenuItem("Confirm reception of replacement shipment", () -> {
            boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the replacement shipment");
            if (confirmation) {
                OperationResult result = ticketController.confirmReceptionOfReplacement(ticketId);
                System.out.println(prettify(result.message()));
                waitForKey();
            } else {
                System.out.println(prettify("Action cancelled"));
                waitForKey();
            }
        }, () -> ticket.getState() == TicketState.ReplacementInTransit));

        prettyDynamicMenu("Select action", "Go back", options, () -> displayTicket(ticket));

        // Setup action menu
        //        int answer = prettyMenu("Select action", options);
        //        switch (answer) {
        //            case 0 -> {
        //                return;
        //            }
        //            case 1 -> {
        //                boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the replacement shipment");
        //                if (confirmation) {
        //                    OperationResult result = ticketController.confirmReceptionOfReplacement(ticketId);
        //                    System.out.println(prettify(result.message()));
        //                } else {
        //                    System.out.println(prettify("Action cancelled"));
        //                    waitForKey();
        //                }
        //            }
        //        }
    }

    /**
     * Displays the available actions for a seller related to the specified ticket.
     * Allows the seller to set a suggested solution, confirm the reception of a return shipment,
     * and create a replacement shipment.
     *
     * @param ticket The ticket for which actions are displayed.
     */
    public void displaySellerActions(Ticket ticket) {
        ArrayList<DynamicMenuItem> options = new ArrayList<>();

        options.add(new DynamicMenuItem("View related orders", () -> {
            List<Order> orderList = new ArrayList<>();
            orderList.add(shopController.getOrder(ticket.getOrderId()));

            if (ticket.getReplacementOrderId() != null) {
                orderList.add(shopController.getOrder(ticket.getReplacementOrderId()));
                System.out.println(prettify("Problem order is first. Replacement order is second."));
                waitForKey();
            }
            shopController.displayPendingSellerOrders(orderList);
        }, () -> true));

        options.add(new DynamicMenuItem("Set suggested solution", () -> {
            boolean requireReturn = Utils.prettyPromptBool("Request that the buyer return the product(s)?");
            boolean offerReplacement = Utils.prettyPromptBool("Offer to replace the product(s)?");
            String suggestedSolution = prettyPrompt("Suggested solution", Utils::validateNotEmpty);

            if (requireReturn) {
                String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
                String trackingNumber = prettyPrompt("Tracking number of replacement shipment", Utils::validateNotEmpty);

                if (offerReplacement) {
                    System.out.println(prettify(ticketController.changeTicketToReturnAndReplace(ticketId, suggestedSolution, shippingCompany, trackingNumber).message()));
                } else {
                    System.out.println(prettify(ticketController.changeTicketToReturnWithoutReplace(ticketId, suggestedSolution, shippingCompany, trackingNumber).message()));
                }
            } else {
                if (offerReplacement) {
                    System.out.println(prettify(ticketController.changeTicketToReplaceWithoutReturn(ticketId, suggestedSolution).message()));
                } else {
                    System.out.println(prettify(ticketController.changeTicketToNoReturnNoReplace(ticketId, suggestedSolution).message()));
                }
            }
            waitForKey();
        }, () -> ticket.getState() == TicketState.OpenManual));

        options.add(new DynamicMenuItem("Confirm reception of return shipment", () -> {
            boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the return shipment");
            if (confirmation) {
                OperationResult result = ticketController.confirmReceptionOfReturn(ticketId);
                System.out.println(prettify(result.message()));
                waitForKey();
            } else {
                System.out.println(prettify("Action cancelled"));
                waitForKey();
            }
        }, () -> ticket.getState() == TicketState.ReturnInTransit));

        options.add(new DynamicMenuItem("Create replacement shipment", () -> {
            String replacementProductDescription = prettyPrompt("Replacement product description", Utils::validateNotEmpty);
            String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
            String trackingNumber = prettyPrompt("Tracking number of replacement shipment", Utils::validateNotEmpty);
            LocalDate expectedDeliveryDate = prettyPromptDate("Expected delivery date");

            OperationResult result = ticketController.createReplacementShipment(ticketId, replacementProductDescription, trackingNumber, expectedDeliveryDate, shippingCompany);
            System.out.println(prettify(result.message()));
            waitForKey();
        }, () -> ticket.getState() == TicketState.ReturnReceived));

        prettyDynamicMenu("Select action", "Go back", options, () -> displayTicket(ticket));

        //        String[] options = {"Go back", "Set suggested solution", "Confirm reception of return shipment", "Create replacement shipment"};

        //        clearConsole();
        //        displayTicket(ticket);
        //
        //        // Setup action menu
        //        int answer = prettyMenu("Select action", options);
        //        switch (answer) {
        //            case 0 -> {
        //                return;
        //            }
        //            case 1 -> {
        //                boolean requireReturn = Utils.prettyPromptBool("Request that the buyer return the product(s)?");
        //                boolean offerReplacement = Utils.prettyPromptBool("Offer to replace the product(s)?");
        //                String suggestedSolution = prettyPrompt("Suggested solution", Utils::validateNotEmpty);
        //
        //                if (requireReturn) {
        //                    String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
        //                    String trackingNumber = prettyPrompt("Tracking number of replacement shipment", Utils::validateNotEmpty);
        //
        //                    if (offerReplacement) {
        //                        ticketController.changeTicketToReturnAndReplace(ticketId, suggestedSolution, shippingCompany, trackingNumber);
        //                    }
        //                    else {
        //                        ticketController.changeTicketToReturnWithoutReplace(ticketId, suggestedSolution, shippingCompany, trackingNumber);
        //                    }
        //                }
        //                else {
        //                    if (offerReplacement) {
        //                        ticketController.changeTicketToReplaceWithoutReturn(ticketId, suggestedSolution);
        //                    }
        //                    else {
        //                        ticketController.changeTicketToNoReturnNoReplace(ticketId, suggestedSolution);
        //                    }
        //                }
        //            }
        //            case 2 -> {
        //                boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the return shipment");
        //                if (confirmation) {
        //                    OperationResult result = ticketController.confirmReceptionOfReturn(ticketId);
        //                    System.out.println(prettify(result.message()));
        //                } else {
        //                    System.out.println(prettify("Action cancelled"));
        //                    waitForKey();
        //                }
        //            }
        //            case 3 -> {
        //                String replacementProductDescription = prettyPrompt("Replacement product description", Utils::validateNotEmpty);
        //                String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
        //                String trackingNumber = prettyPrompt("Tracking number of replacement shipment", Utils::validateNotEmpty);
        //                LocalDate expectedDeliveryDate = prettyPromptDate("Expected delivery date");
        //
        //                OperationResult result = ticketController.createReplacementShipment(ticketId, replacementProductDescription, trackingNumber, expectedDeliveryDate, shippingCompany);
        //                System.out.println(prettify(result.message()));
        //            }
        //        }
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
        LocalDate orderDate = shopController.getOrder(ticket.getOrderId()).getOrderDate();
        System.out.println(prettify("For order placed on: " + orderDate));
        String buyerName = profileController.getBuyer(ticket.getBuyerId()).getUsername();
        System.out.println(prettify("Buyer: " + buyerName));
        String sellerName = profileController.getSeller(ticket.getSellerId()).getName();
        System.out.println(prettify("Seller: " + sellerName));
        System.out.println(prettify("Number of products in ticket: " + ticket.getProducts().size()));
        System.out.println(prettify("Cause of ticket: " + ticket.getCause()));
        System.out.println(prettify("Problem description: " + ticket.getProblemDescription()));
        System.out.println(prettify("Suggested solution: " + ticket.getSuggestedSolution()));
        System.out.println(prettify("Replacement product description: " + ticket.getReplacementProductDescription()));

        Order replacementOrder = shopController.getOrder(ticket.getReplacementOrderId());
        if (replacementOrder != null) {
            System.out.println(prettify("Replacement products: "));
            for (Tuple<Product, Integer> productTuple : replacementOrder.getProducts()) {
                System.out.println(prettify(productTuple.second + "x " + productTuple.first.getTitle()));
            }

            if (ticket.getState().equals(TicketState.ReplacementInTransit)) {
                System.out.println(prettify("Replacement shipment creation date: " + replacementOrder.getShipment().getCreationDate()));
                System.out.println(prettify("Replacement shipment expected delivery date: " + replacementOrder.getShipment().getExpectedDeliveryDate()));
                System.out.println(prettify("Replacement shipment company: " + replacementOrder.getShipment().getShippingCompany()));
                System.out.println(prettify("Replacement shipment tracking number: " + replacementOrder.getShipment().getTrackingNumber()));
            }
        }

        if (ticket.getState().equals(TicketState.ReturnInTransit)) {
            System.out.println(prettify("Return shipment creation date: " + ticket.getReturnShipment().getCreationDate()));
            System.out.println(prettify("Return shipment company: " + ticket.getReturnShipment().getShippingCompany()));
            System.out.println(prettify("Return shipment tracking number: " + ticket.getReturnShipment().getTrackingNumber()));
        }

        waitForKey();
    }
}
