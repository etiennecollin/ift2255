/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.models.Session;
import com.etiennecollin.ift2255.clientCLI.models.ShopModel;
import com.etiennecollin.ift2255.clientCLI.models.TicketingModel;
import com.etiennecollin.ift2255.clientCLI.models.data.*;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;
import com.etiennecollin.ift2255.clientCLI.views.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller class responsible for handling operations related to tickets.
 */
public class TicketController {
    /**
     * The view renderer to display views.
     */
    private final ViewRenderer renderer;
    /**
     * The ticketing models to interact with ticket data.
     */
    private final TicketingModel ticketModel;
    /**
     * The shop model to interact with shop-related data.
     */
    private final ShopModel shopModel;

    /**
     * Constructs a new TicketController.
     *
     * @param renderer    The view renderer to display views.
     * @param ticketModel The ticketing models to interact with ticket data.
     * @param shopModel   The shop model to interact with shop-related data.
     */
    public TicketController(ViewRenderer renderer, TicketingModel ticketModel, ShopModel shopModel) {
        this.renderer = renderer;
        this.ticketModel = ticketModel;
        this.shopModel = shopModel;
    }

    /**
     * Displays the details of a specific ticket.
     *
     * @param ticketId The UUID of the ticket to display.
     */
    public void displayTicket(UUID ticketId) {
        renderer.addNextView(new TicketDisplay(ticketId, this, UniShop.getInstance().getProfileController(), UniShop.getInstance().getShopController()), true);
    }

    /**
     * Displays a menu with a list of tickets.
     */
    public void displayTickets() {
        renderer.addNextView(new TicketsMenu(this, UniShop.getInstance().getProfileController(), UniShop.getInstance().getShopController()), true);
    }

    /**
     * Displays the ticket creation view.
     * @param orderId The UUID of the order for which to create a ticket.
     */
    public void displayTicketCreation(UUID orderId) {
        renderer.addNextView(new TicketCreation(orderId, this), false);
    }

    // TODO javadoc
    public void displayProductReturnCreation(UUID orderId) {
        renderer.addNextView(new ProductReturnMenu(orderId, this, UniShop.getInstance().getShopController()), false);
    }

    // TODO javadoc
    public void displayProductExchangeCreation(UUID orderId) {
        renderer.addNextView(new ProductExchangeMenu(orderId, this, UniShop.getInstance().getShopController()), true);
    }

    /**
     * Retrieves a specific ticket by its UUID.
     *
     * @param ticketId The UUID of the ticket to retrieve.
     *
     * @return The ticket with the specified UUID.
     */
    public Ticket getTicket(UUID ticketId) {
        return ticketModel.getTicket(ticketId);
    }

    public Ticket getExchangeTicket() {
        return Session.getInstance().getExchangeTicket();
    }

    public List<CartProduct> getExchangeCart() {
        Database exchangeCart = Session.getInstance().getExchangeCart();
        if (exchangeCart == null) {
            return new ArrayList<>();
        }
        return exchangeCart.get(null, (p) -> true);
    }

    /**
     * Retrieves a list of tickets based on the user's role (Buyer or Seller).
     *
     * @return A list of tickets associated with the user.
     */
    public List<Ticket> getTickets() {
        UUID userId = Session.getInstance().getUserId();

        if (Session.getInstance().getUserType() == UserType.Buyer) {
            return ticketModel.getTickets((ticket) -> ticket.getBuyerId() == userId);
        } else {
            return ticketModel.getTickets((ticket) -> ticket.getSellerId() == userId);
        }
    }

    // TODO javadoc
    public OperationResult createManualTicket(UUID orderId, String description, TicketCause cause) {
        Order order = shopModel.getOrder(orderId);
        if (order == null) {
            return new OperationResult(false, "Cannot create ticket because the order does not exist.");
        }

        return ticketModel.createManualTicket(orderId, description, cause);
    }

    // TODO javadoc
    public OperationResult createReturnTicket(UUID orderId, ArrayList<Tuple<Product, Integer>> products, TicketCause cause) {
        return ticketModel.createAutoTicket(orderId, products, cause, null);
    }

    public OperationResult startExchangeProcess(UUID orderId, ArrayList<Tuple<Product, Integer>> products, TicketCause cause) {
        cancelExchangeProcess();

        Session session = Session.getInstance();
        session.setInExchangeProcess(true);
        session.createExchangeCart();
        Order order = shopModel.getOrder(orderId);
        session.setExchangeTicket(new Ticket("", orderId, products, cause, TicketState.OpenAuto, order.getBuyerId(), order.getSellerId()));

        renderer.addNextView(new OrderExchangeMenu(this, UniShop.getInstance().getShopController()), true);
        renderer.addNextView(new ProductsMenu(order.getSellerId(), UniShop.getInstance().getShopController()), true);

        return new OperationResult(true, "Please select the replacement items for the exchange.");
    }

    public void cancelExchangeProcess() {
        Session session = Session.getInstance();
        session.setInExchangeProcess(false);
        session.deleteExchangeCart();
        session.setExchangeTicket(null);
        session.setExchangeOrder(null);
    }

    public void completeExchangeProcess() {
        Session session = Session.getInstance();

        if (session.getIsInExchangeProcess()) {
            Ticket ticket = session.getExchangeTicket();
            Order originalOrder = shopModel.getOrder(ticket.getOrderId());

            ticketModel.activateExchangeTicket(session.getExchangeTicket(), originalOrder, getExchangeCart());
            cancelExchangeProcess();
        }
    }


    public OperationResult changeTicketToReturnAndReplace(UUID ticketId, String solution, String shippingCompany, String trackingNumber) {
        return ticketModel.changeTicketToReplacement(ticketId, solution, true, trackingNumber, shippingCompany);
    }

    public OperationResult changeTicketToReturnWithoutReplace(UUID ticketId, String solution, String shippingCompany, String trackingNumber) {
        return ticketModel.changeTicketToNoReplacement(ticketId, solution, true, trackingNumber, shippingCompany);
    }

    public OperationResult changeTicketToReplaceWithoutReturn(UUID ticketId, String solution) {
        return ticketModel.changeTicketToReplacement(ticketId, solution, false, null, null);
    }

    public OperationResult changeTicketToNoReturnNoReplace(UUID ticketId, String solution) {
        return ticketModel.changeTicketToNoReplacement(ticketId, solution, false, null, null);
    }

    public OperationResult confirmReceptionOfReplacement(UUID ticketId) {
        return ticketModel.confirmReceptionOfReplacement(ticketId);
    }

    public OperationResult confirmReceptionOfReturn(UUID ticketId) {
        return ticketModel.confirmReceptionOfReturn(ticketId);
    }

//    /**
//     * Creates a return shipment for a ticket.
//     *
//     * @param ticketId        The UUID of the ticket for which to create a return shipment.
//     * @param trackingNumber  The tracking number of the return shipment.
//     * @param deliveryDate    The delivery date of the return shipment.
//     * @param shippingCompany The shipping company used for the return shipment.
//     *
//     * @return The result of the operation (success or failure).
//     */
//    public OperationResult createReturnShipment(UUID ticketId, String trackingNumber, String shippingCompany) {
//        return ticketModel.createReturnShipment(ticketId, trackingNumber, null, shippingCompany);
//    }

    /**
     * Creates a replacement shipment for a ticket.
     *
     * @param ticketId        The UUID of the ticket for which to create a replacement shipment.
     * @param description     The description of the replacement product(s).
     * @param trackingNumber  The tracking number of the replacement shipment.
     * @param deliveryDate    The delivery date of the replacement shipment.
     * @param shippingCompany The shipping company used for the replacement shipment.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult createReplacementShipment(UUID ticketId, String description, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        return ticketModel.createReplacementShipment(ticketId, description, trackingNumber, deliveryDate, shippingCompany);
    }
}
