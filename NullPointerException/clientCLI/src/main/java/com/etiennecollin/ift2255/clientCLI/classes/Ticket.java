/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ticket {
    private static final int MAX_RETURN_DELAY_DAYS = 30;
    private final Buyer buyer;
    private final Seller seller;
    private LocalDate returnTrackingNumberEmissionDate;
    private ArrayList<Product> products;
    private Order order;
    private String problemDescription;
    private String suggestedSolution;
    private String replacementProductDescription;
    private boolean returnDeliveryConfirmed;
    private boolean replacementDeliveryConfirmed;
    private int returnTrackingNumber;
    private TicketState state;
    public Ticket(String problemDescription, Order order, ArrayList<Product> products, Buyer buyer, Seller seller) {
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Seller getSeller() {
        return seller;
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
}