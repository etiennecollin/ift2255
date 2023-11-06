/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Tuple<Product, Integer>> products;
    /**
     * Total value of cart in cents.
     */
    private int totalPrice;
    private int totalFidelityPoints;

    public Cart() {
        this.totalPrice = 0;
        this.totalFidelityPoints = 0;
    }

    public void addProduct(Product product, int quantity) {
        for (Tuple<Product, Integer> tuple : this.products) {
            if (tuple.first == product) {
                totalPrice += product.getPrice();
                totalFidelityPoints += product.getFidelityPoints();
                tuple.second += quantity;
                return;
            }
        }
        this.products.add(new Tuple<>(product, quantity));
    }

    public void subtractProduct(Product product, int quantity) throws IllegalArgumentException {
        for (int i = 0; i < this.products.size(); i++) {
            Tuple<Product, Integer> tuple = this.products.get(i);
            if (tuple.first == product) {
                totalPrice -= product.getPrice();
                totalFidelityPoints -= product.getFidelityPoints();
                if (tuple.second <= quantity) {
                    this.products.remove(i);
                } else {
                    tuple.second -= quantity;
                }
                break;
            }
        }
        throw new IllegalArgumentException("Product not in cart.");
    }

    public void removeProduct(Product product) throws IllegalArgumentException {
        for (int i = 0; i < this.products.size(); i++) {
            Tuple<Product, Integer> tuple = this.products.get(i);
            if (tuple.first == product) {
                totalPrice -= product.getPrice() * tuple.second;
                totalFidelityPoints -= product.getFidelityPoints() * tuple.second;
                this.products.remove(i);
                break;
            }
        }
        throw new IllegalArgumentException("Product not in cart.");
    }

    public Order createOrder(String email, int phone, String address, String billingAddress, String creditCardName, int creditCardNumber, int creditCardExp, int creditCardSecretDigits) {
        Order order = new Order(this.getTotalPrice(), this.getTotalFidelityPoints(), this.getProducts(), email, phone, address, billingAddress, creditCardName, creditCardNumber, creditCardExp, creditCardSecretDigits);
        emptyCart();
        return order;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalFidelityPoints() {
        return totalFidelityPoints;
    }

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    public void emptyCart() {
        this.products.clear();
        this.totalFidelityPoints = 0;
        this.totalPrice = 0;
    }
}
