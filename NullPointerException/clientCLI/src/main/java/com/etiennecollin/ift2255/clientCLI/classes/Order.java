/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private static final int PRODUCTION_TIME_DAYS = 3;
    private final UUID id;
    private final LocalDate orderDate;
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
    private LocalDate deliveryDate;

    public Order(int cost, int fidelityPoints, ArrayList<Tuple<Product, Integer>> products, String email, int phone, String address, String billingAddress, String creditCardName, int creditCardNumber, int creditCardExp, int creditCardSecretDigits) {
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
        this.state = OrderState.InProduction;
        this.orderDate = LocalDate.now();
        // Calculate the date 7 days from now
        this.deliveryDate = LocalDate.now().plusDays(7);
        this.id = UUID.randomUUID();
    }

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Tuple<Product, Integer>> products) {
        this.products = products;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderState getState() {
        // Check if product production is done
        // If so, set status to "in transit"
        if (LocalDate.now().isAfter(orderDate.plusDays(PRODUCTION_TIME_DAYS))) {
            this.setInTransit();
        }
        return state;
    }

    public void setInTransit() {
        this.state = OrderState.InTransit;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public UUID getId() {
        return id;
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
