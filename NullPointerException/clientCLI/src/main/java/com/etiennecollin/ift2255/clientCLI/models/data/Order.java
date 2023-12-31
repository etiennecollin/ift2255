/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents an order, extending DatabaseObject.
 */
public class Order extends DatabaseObject {
    /**
     * The date when the order was placed.
     */
    private final LocalDate orderDate;
    /**
     * The ID of the buyer placing the order.
     */
    private final UUID buyerId;
    /**
     * The ID of the seller fulfilling the order.
     */
    private final UUID sellerId;
    /**
     * The total cost of the order.
     */
    private final int totalCost;
    /**
     * The amount of fidelity points earned with the order.
     */
    private final int fidelityPointsEarned;
    /**
     * The email associated with the order.
     */
    private final String email;
    /**
     * The phone number associated with the order.
     */
    private final String phone;
    /**
     * The shipping address for the order.
     */
    private final String address;
    /**
     * The billing address for the order.
     */
    private final String billingAddress;
    /**
     * The name on the credit card used for payment.
     */
    private final String creditCardName;
    /**
     * The credit card number used for payment.
     */
    private final String creditCardNumber;
    /**
     * The expiration date of the credit card used for payment.
     */
    private final YearMonth creditCardExp;
    /**
     * The last three digits of the credit card used for payment.
     */
    private final String creditCardSecretDigits;
    /**
     * The payment method used for the order.
     */
    private final PaymentMethod paymentMethod;
    /**
     * The list of products included in the order along with their quantities.
     */
    private final ArrayList<Tuple<Product, Integer>> products;
    /**
     * The current state of the order.
     */
    private OrderState state;
    /**
     * The shipment associated with the order.
     */
    private Shipment shipment;

    /**
     * Constructs an Order object with the specified parameters.
     *
     * @param products               The list of products included in the order.
     * @param totalCost              The total cost of the order.
     * @param fidelityPointsEarned   The amount of fidelity points earned with the order.
     * @param paymentMethod          The payment method used for the order.
     * @param email                  The email associated with the order.
     * @param phone                  The phone number associated with the order.
     * @param address                The shipping address for the order.
     * @param billingAddress         The billing address for the order.
     * @param creditCardName         The name on the credit card used for payment.
     * @param creditCardNumber       The credit card number used for payment.
     * @param creditCardExp          The expiration date of the credit card used for payment.
     * @param creditCardSecretDigits The last three digits of the credit card used for payment.
     * @param buyerId                The ID of the buyer placing the order.
     * @param sellerId               The ID of the seller fulfilling the order.
     */
    public Order(ArrayList<Tuple<Product, Integer>> products, int totalCost, int fidelityPointsEarned, PaymentMethod paymentMethod, String email, String phone, String address, String billingAddress, String creditCardName, String creditCardNumber, YearMonth creditCardExp, String creditCardSecretDigits, UUID buyerId, UUID sellerId) {
        this.products = products;
        this.totalCost = totalCost;
        this.fidelityPointsEarned = fidelityPointsEarned;
        this.paymentMethod = paymentMethod;
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
        this.shipment = null;

        this.state = OrderState.InProduction;
        this.orderDate = LocalDate.now();
    }

    /**
     * Gets the list of products included in the order.
     *
     * @return ArrayList containing tuples of products and their quantities.
     */
    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    /**
     * Gets the date when the order was placed.
     *
     * @return The LocalDate representing the order date.
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Gets the ID of the buyer placing the order.
     *
     * @return The UUID representing the buyer's ID.
     */
    public UUID getBuyerId() {
        return buyerId;
    }

    /**
     * Gets the ID of the seller fulfilling the order.
     *
     * @return The UUID representing the seller's ID.
     */
    public UUID getSellerId() {
        return sellerId;
    }

    /**
     * Gets the total cost of the order.
     *
     * @return The integer representing the total cost.
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * Gets the amount of fidelity points earned with the order.
     *
     * @return The integer representing the fidelity points earned.
     */
    public int getFidelityPointsEarned() {
        return fidelityPointsEarned;
    }

    /**
     * Gets the email associated with the order.
     *
     * @return The String representing the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the phone number associated with the order.
     *
     * @return The String representing the phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the shipping address for the order.
     *
     * @return The String representing the shipping address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the billing address for the order.
     *
     * @return The String representing the billing address.
     */
    public String getBillingAddress() {
        return billingAddress;
    }

    /**
     * Gets the name on the credit card used for payment.
     *
     * @return The String representing the credit card name.
     */
    public String getCreditCardName() {
        return creditCardName;
    }

    /**
     * Gets the credit card number used for payment.
     *
     * @return The String representing the credit card number.
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Gets the last three digits of the credit card used for payment.
     *
     * @return The String representing the credit card secret digits.
     */
    public String getCreditCardSecretDigits() {
        return creditCardSecretDigits;
    }

    /**
     * Gets the expiration date of the credit card used for payment.
     *
     * @return The YearMonth representing the credit card expiration date.
     */
    public YearMonth getCreditCardExp() {
        return creditCardExp;
    }

    /**
     * Gets the current state of the order.
     *
     * @return The OrderState representing the current state.
     */
    public OrderState getState() {
        return state;
    }

    /**
     * Sets the state of the order.
     *
     * @param state The OrderState to set for the order.
     */
    public void setState(OrderState state) {
        this.state = state;
    }

    /**
     * Gets the shipment associated with the order.
     *
     * @return The Shipment representing the order shipment.
     */
    public Shipment getShipment() {
        return shipment;
    }

    /**
     * Sets the shipment associated with the order.
     *
     * @param shipment The Shipment to set for the order.
     */
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    /**
     * Gets the payment method used for the order.
     *
     * @return The PaymentMethod representing the order payment method.
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
