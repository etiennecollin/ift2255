/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final LocalDate orderDate;
    private final Buyer buyer;
    private int cost;
    private int fidelityPoints;
    private ArrayList<Tuple<Product, Integer>> products;
    private String email;
    private int phone;
    private String address;
    private String billingAddress;
    private String creditCardName;
    private int creditCardNumber;
    private int creditCardExp;
    private int creditCardSecretDigits;
    private OrderState state;
    private ShippingInfo shippingInfo;

    public Order(int cost, int fidelityPoints, ArrayList<Tuple<Product, Integer>> products, String email, int phone, String address, String billingAddress, String creditCardName, int creditCardNumber, int creditCardExp, int creditCardSecretDigits, Buyer buyer) {
        this.cost = cost;
        this.fidelityPoints = fidelityPoints;
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

        this.state = OrderState.InProduction;
        this.orderDate = LocalDate.now();
        this.id = UUID.randomUUID();
    }

    public void createTicket(String description, ArrayList<Product> products) {
        // Check if order can still be reported
        if (LocalDate.now().isAfter(this.getOrderDate().plusYears(1))) {
            throw new IllegalArgumentException("This order can no longer be reported");
        }

        // Check if products are in order
        for (Tuple<Product, Integer> tuple : this.getProducts()) {
            if (!products.contains(tuple.first)) {
                throw new IllegalArgumentException("Some products are not part of this order");
            }
        }

        // "Sort" products by seller
        HashMap<Seller, ArrayList<Product>> hashmap = new HashMap<>();
        for (Product product : products) {
            ArrayList<Product> newValue = hashmap.getOrDefault(product.getSeller(), new ArrayList<>());
            newValue.add(product);
            hashmap.put(product.getSeller(), newValue);
        }

        // Create ticket per seller and add it to buyer and seller
        hashmap.forEach((seller, value) -> {
            Ticket ticket = new Ticket(description, this, value, this.buyer, seller);
            this.buyer.addTicket(ticket);
            seller.addTicket(ticket);
        });
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

    public void createTicket(String description) {
        // Check if order can still be reported
        if (LocalDate.now().isAfter(this.getOrderDate().plusYears(1))) {
            throw new IllegalArgumentException("This order can no longer be reported");
        }

        // "Sort" products by seller
        HashMap<Seller, ArrayList<Product>> hashmap = new HashMap<>();
        for (Tuple<Product, Integer> tuple : this.products) {
            Product product = tuple.first;
            ArrayList<Product> newValue = hashmap.getOrDefault(product.getSeller(), new ArrayList<>());
            newValue.add(product);
            hashmap.put(product.getSeller(), newValue);
        }

        // Create ticket per seller and add it to buyer and seller
        hashmap.forEach((seller, value) -> {
            Ticket ticket = new Ticket(description, this, value, this.buyer, seller);
            this.buyer.addTicket(ticket);
            seller.addTicket(ticket);
        });
    }

    public ShippingInfo getShipping() {
        return shippingInfo;
    }

    public void setShipping(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public OrderState getState() {
        return state;
    }

    public void setInTransit(String shippingCompany, int trackingNumber, LocalDate expectedDeliveryDate) {
        this.shippingInfo = new ShippingInfo(shippingCompany, trackingNumber, expectedDeliveryDate);
        String title = "Order " + getId() + " shipped!";
        String description = "Shipped by: " + shippingCompany + "\nTracking number: " + trackingNumber;
        getBuyer().addNotification(new Notification(title, description));
        this.state = OrderState.InTransit;
    }

    public UUID getId() {
        return id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setInProduction() {
        this.state = OrderState.InProduction;
    }

    public void setDelivered() {
        this.state = OrderState.Delivered;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
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

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public int getCreditCardExp() {
        return creditCardExp;
    }

    public void setCreditCardExp(int creditCardExp) {
        this.creditCardExp = creditCardExp;
    }

    public int getCreditCardSecretDigits() {
        return creditCardSecretDigits;
    }

    public void setCreditCardSecretDigits(int creditCardSecretDigits) {
        this.creditCardSecretDigits = creditCardSecretDigits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
