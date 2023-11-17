/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Ticket implements Serializable {
    private static final int MAX_RETURN_DELAY_DAYS = 30;
    private final Buyer buyer;
    private final Seller seller;
    private LocalDate returnTrackingNumberEmissionDate;
    private ArrayList<Tuple<Product, Integer>> products;
    private Order order;
    private String problemDescription;
    private String suggestedSolution;
    private String replacementProductDescription;
    private boolean returnDeliveryConfirmed;
    private boolean replacementDeliveryConfirmed;
    private int returnTrackingNumber;
    private TicketState state;

    public Ticket(String problemDescription, Order order, ArrayList<Tuple<Product, Integer>> products, Buyer buyer, Seller seller) {
        this.products = products;
        this.order = order;
        this.buyer = buyer;
        this.seller = seller;
        this.problemDescription = problemDescription;
        this.suggestedSolution = "";
        this.replacementProductDescription = "";
        this.returnDeliveryConfirmed = false;
        this.replacementDeliveryConfirmed = false;
        openTicket();
        buyer.addTicket(this);
        seller.addTicket(this);
    }

    public void openTicket() {
        this.state = TicketState.Open;
    }

    public LocalDate getReturnTrackingNumberEmissionDate() {
        return returnTrackingNumberEmissionDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void confirmReturnReceival() {
        this.returnDeliveryConfirmed = true;
    }

    public int getReturnTrackingNumber() {
        return returnTrackingNumber;
    }

    public TicketState getState() {
        if (!LocalDate.now().isAfter(returnTrackingNumberEmissionDate.plusDays(MAX_RETURN_DELAY_DAYS)) && !returnDeliveryConfirmed) {
            cancelTicket();
        }

        return state;
    }

    public void cancelTicket() {
        this.state = TicketState.Cancelled;
    }

    public void emitReturnTrackingNumber(int trackingNumber) {
        this.returnTrackingNumberEmissionDate = LocalDate.now();
        this.returnTrackingNumber = trackingNumber;
    }

    public void closeTicket() {
        this.state = TicketState.Closed;
    }

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Tuple<Product, Integer>> products) {
        this.products = products;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getReplacementProductDescription() {
        return replacementProductDescription;
    }

    public void setReplacementProductDescription(String replacementProductDescription) {
        this.replacementProductDescription = replacementProductDescription;
    }

    public boolean isReturnDeliveryConfirmed() {
        return returnDeliveryConfirmed;
    }

    public void setReturnDeliveryConfirmed(boolean returnDeliveryConfirmed) {
        this.returnDeliveryConfirmed = returnDeliveryConfirmed;
    }

    public boolean isReplacementDeliveryConfirmed() {
        return replacementDeliveryConfirmed;
    }

    public void setReplacementDeliveryConfirmed(boolean replacementDeliveryConfirmed) {
        this.replacementDeliveryConfirmed = replacementDeliveryConfirmed;
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
        this.suggestedSolution = suggestedSolution;

        String title = "New solution for one of your tickets";
        String content = "Ticket: " + this.getProblemDescription() + "\nSolution suggested: " + this.getSuggestedSolution();
        Notification notification = new Notification(title, content);
        this.buyer.addNotification(notification);
    }
}