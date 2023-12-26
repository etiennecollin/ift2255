/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Ticket extends DatabaseObject {
    private static final int MAX_RETURN_DELAY_DAYS = 30;
    private final UUID buyerId;
    private final UUID sellerId;
    private final UUID id;
    private final LocalDate creationDate;
    private Shipment returnShipment;
    private Shipment replacementShipment;
    private ArrayList<Tuple<Product, Integer>> products;
    private Order order;
    private String problemDescription;
    private final String suggestedSolution;
    private String replacementProductDescription;
    private TicketState state;
    private TicketCause cause;

    public Ticket(String problemDescription, Order order, ArrayList<Tuple<Product, Integer>> products, TicketCause cause, TicketState state, UUID buyerId, UUID sellerId) {
        this.products = products;
        this.order = order;
        this.cause = cause;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.problemDescription = problemDescription;
        this.suggestedSolution = "";
        this.replacementProductDescription = "";
        this.returnShipment = null;
        this.replacementShipment = null;
        this.state = state;
        //        if (LocalDate.now().isAfter(order.getOrderDate().plusDays(30))) {
        //            this.state = TicketState.OpenAuto;
        //        } else {
        //            this.state = TicketState.OpenManual;
        //        }

        this.id = UUID.randomUUID();
        this.creationDate = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(getId(), ticket.getId());
    }

    public UUID getId() {
        return id;
    }

    public TicketCause getCause() {
        return cause;
    }

    public void setCause(TicketCause cause) {
        this.cause = cause;
    }

    public Shipment getReturnShipment() {
        return returnShipment;
    }

    public void setReturnShipment(Shipment returnShipment) {
        this.returnShipment = returnShipment;
    }

    public Shipment getReplacementShipment() {
        return replacementShipment;
    }

    public void setReplacementShipment(Shipment replacementShipment) {
        this.replacementShipment = replacementShipment;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TicketState getState() {
        updateState();
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public void updateState() {
        //        if (state.equals(TicketState.Cancelled) || state.equals(TicketState.Closed)) {
        //            return;
        //        }
        //
        //        boolean afterDueDate = LocalDate.now().isAfter(returnShipment.getCreationDate().plusDays(MAX_RETURN_DELAY_DAYS));
        //        if (!state.equals(TicketState.ReturnInTransit) && !state.equals(TicketState.ReturnReceived) && afterDueDate) {
        //            this.state = TicketState.Cancelled;
        //        } else if (state.equals(TicketState.ReturnInTransit) && returnShipment.isDeliveryConfirmed()) {
        //            this.state = TicketState.ReturnReceived;
        //            // Add products back to inventory if products were not malfunctioning
        //            if (!cause.equals(TicketCause.MalfunctioningProduct)) {
        //                for (Tuple<Product, Integer> tuple : products) {
        //                    Product product = tuple.first;
        //                    int quantity = tuple.second;
        //                    product.setQuantity(product.getQuantity() + quantity);
        //                }
        //            }
        //        } else if (state.equals(TicketState.ReturnReceived) && replacementShipment.isDeliveryConfirmed()) {
        //            this.state = TicketState.Closed;
        //        }
    }

    //    public void createReturnShipment(String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
    //        this.state = TicketState.ReturnInTransit;
    //        this.returnShipment = new Shipment(trackingNumber, deliveryDate, shippingCompany);
    //    }
    //
    //    public void createReplacementShipment(String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
    //        this.state = TicketState.ReplacementInTransit;
    //        this.replacementShipment = new Shipment(trackingNumber, deliveryDate, shippingCompany);
    //    }

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Tuple<Product, Integer>> products) {
        this.products = products;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public String getReplacementProductDescription() {
        return replacementProductDescription;
    }

    public void setReplacementProductDescription(String replacementProductDescription) {
        this.replacementProductDescription = replacementProductDescription;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getSuggestedSolution() {
        return suggestedSolution;
    }

    public void setSuggestedSolution(String suggestedSolution) {
        //        this.suggestedSolution = suggestedSolution;
        //
        //        String title = "New solution for one of your tickets";
        //        String content = "Ticket: " + this.getProblemDescription() + "\nSolution suggested: " + this.getSuggestedSolution();
        //        Notification notification = new Notification(title, content);
        //        this.buyer.addNotification(notification);
    }
}