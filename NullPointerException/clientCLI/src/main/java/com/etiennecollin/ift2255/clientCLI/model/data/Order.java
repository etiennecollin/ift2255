/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.UUID;

public class Order extends DatabaseObject {
    private final LocalDate orderDate;
    private final UUID buyerId;
    private final UUID sellerId;
    private int totalCost;
//    private int numberOfProducts;
    private int fidelityPointsEarned;
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
    private ArrayList<Tuple<Product, Integer>> products;

    public Order(ArrayList<Tuple<Product, Integer>> products, int totalCost, int fidelityPointsEarned, PayementMethod payementMethod, String email, String phone, String address, String billingAddress, String creditCardName, String creditCardNumber, YearMonth creditCardExp, String creditCardSecretDigits, UUID buyerId, UUID sellerId) {
        this.products = products;
        this.totalCost = totalCost;
        this.fidelityPointsEarned = fidelityPointsEarned;
        this.payementMethod = payementMethod;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.billingAddress = billingAddress;
        this.creditCardName = creditCardName;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExp = creditCardExp;
        this.creditCardSecretDigits = creditCardSecretDigits;
        this.buyerId = buyerId;
        this.sellerId = sellerId;

        this.state = OrderState.InProduction;
        this.orderDate = LocalDate.now();
    }

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getFidelityPointsEarned() {
        return fidelityPointsEarned;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCreditCardSecretDigits() {
        return creditCardSecretDigits;
    }

    public YearMonth getCreditCardExp() {
        return creditCardExp;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public PayementMethod getPayementMethod() {
        return payementMethod;
    }
}
