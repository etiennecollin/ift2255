/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Order implements Serializable {
    private final UUID id;
    private final LocalDate orderDate;
    private final Buyer buyer;
    private final Seller seller;
    private final ArrayList<Ticket> tickets;
    private int cost;
    private int numberOfProducts;
    private int numberOfFidelityPoints;
    private ArrayList<Tuple<Product, Integer>> products;
    private String email;
    private String phone;
    private String address;
    private String billingAddress;
    private String creditCardName;
    private String creditCardNumber;
    private YearMonth creditCardExp;
    private String creditCardSecretDigits;
    private OrderState state;
    private Shipment shipment;
    private PayementMethod payementMethod;

    public Order(int cost, int numberOfFidelityPoints, PayementMethod payementMethod, ArrayList<Tuple<Product, Integer>> products, String email, String phone, String address, String billingAddress, String creditCardName, String creditCardNumber, YearMonth creditCardExp, String creditCardSecretDigits, Buyer buyer, Seller seller) {
        this.cost = cost;
        this.numberOfFidelityPoints = numberOfFidelityPoints;
        this.payementMethod = payementMethod;
        this.products = products;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.billingAddress = billingAddress;
        this.creditCardName = creditCardName;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExp = creditCardExp;
        this.creditCardSecretDigits = creditCardSecretDigits;
        this.buyer = buyer;
        this.seller = seller;

        for (Tuple<Product, Integer> tuple : products) {
            numberOfProducts += tuple.second;
        }

        this.state = OrderState.InProduction;
        this.tickets = new ArrayList<>();
        this.orderDate = LocalDate.now();
        this.id = UUID.randomUUID();
    }

    public void addTicket(Ticket ticket) throws IllegalArgumentException {
        if (tickets.contains(ticket)) {
            throw new IllegalArgumentException("This ticket is already assigned to this order");
        }
        if (!ticket.getOrder().equals(this)) {
            throw new IllegalArgumentException("This is not linked to this order");
        }

        tickets.add(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId());
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public PayementMethod getPayementMethod() {
        return payementMethod;
    }

    public void setPayementMethod(PayementMethod payementMethod) {
        this.payementMethod = payementMethod;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Seller getSeller() {
        return seller;
    }

    public Ticket createTicket(String description, ArrayList<Tuple<Product, Integer>> products, TicketCause cause, Order exchangeOrder) {
        // Check if order can still be reported
        if (LocalDate.now().isAfter(this.getOrderDate().plusYears(1))) {
            throw new IllegalArgumentException("This order can no longer be reported");
        }

        // Check if products are in order
        for (Tuple<Product, Integer> tuple : products) {
            if (!this.products.contains(tuple)) {
                throw new IllegalArgumentException("Some products are not part of this order");
            }
        }

        // Create ticket per seller and add it to buyer and seller
        Ticket ticket = new Ticket(description, this, products, cause, this.buyer, seller);
        this.buyer.addTicket(ticket);
        seller.addTicket(ticket);

        // Also add notification to seller
        String title = "New ticket opened on one of your orders";
        String content = "Order: " + this.getId() + "\nBuyer: " + this.buyer.getFirstName() + " " + this.buyer.getLastName();
        Notification notification = new Notification(title, content);
        this.seller.addNotification(notification);

        return ticket;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Tuple<Product, Integer>> products) {
        this.products = products;
    }

    public void createTicket(String description, TicketCause cause) {
        // Check if order can still be reported
        if (LocalDate.now().isAfter(this.getOrderDate().plusYears(1))) {
            throw new IllegalArgumentException("This order can no longer be reported");
        }

        // Create ticket per seller and add it to buyer and seller
        Ticket ticket = new Ticket(description, this, this.products, cause, this.buyer, seller);
        this.buyer.addTicket(ticket);
        seller.addTicket(ticket);

        // Also add notification to seller
        String title = "New ticket opened on one of your orders";
        String content = "Order: " + this.getId() + "\nBuyer: " + this.buyer.getFirstName() + " " + this.buyer.getLastName();
        Notification notification = new Notification(title, content);
        this.seller.addNotification(notification);
    }

    public OrderState getState() {
        return state;
    }

    public void setInTransit(String shippingCompany, String trackingNumber, LocalDate expectedDeliveryDate) {
        this.shipment = new Shipment(trackingNumber, expectedDeliveryDate, shippingCompany);
        String title = "Your order is now shipped";
        String description = "Order: " + this.getId() + "\nShipped by: " + shippingCompany + "\nTracking number: " + trackingNumber;
        getBuyer().addNotification(new Notification(title, description));
        this.state = OrderState.InTransit;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setInProduction() {
        this.state = OrderState.InProduction;

        String title = "Your order is now in production";
        String content = "Order: " + this.getId();
        Notification notification = new Notification(title, content);
        this.buyer.addNotification(notification);
    }

    public void setDelivered() {
        String title = "Your order is now delivered";
        String content = "Order: " + this.getId();
        Notification notification = new Notification(title, content);
        this.buyer.addNotification(notification);

        this.state = OrderState.Delivered;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNumberOfFidelityPoints() {
        return numberOfFidelityPoints;
    }

    public void setNumberOfFidelityPoints(int numberOfFidelityPoints) {
        this.numberOfFidelityPoints = numberOfFidelityPoints;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public YearMonth getCreditCardExp() {
        return creditCardExp;
    }

    public void setCreditCardExp(YearMonth creditCardExp) {
        this.creditCardExp = creditCardExp;
    }

    public String getCreditCardSecretDigits() {
        return creditCardSecretDigits;
    }

    public void setCreditCardSecretDigits(String creditCardSecretDigits) {
        this.creditCardSecretDigits = creditCardSecretDigits;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
