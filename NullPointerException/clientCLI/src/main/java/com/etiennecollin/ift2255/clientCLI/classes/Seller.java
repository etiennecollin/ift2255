/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.util.ArrayList;
import java.util.Objects;

public class Seller extends User {
    private final ArrayList<Product> productsOffered;
    private final ArrayList<Order> ordersSold;
    private String name; // Unique

    public Seller(String name, String email, int phone, String address, String password) {
        super(password);
        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
        this.setAddress(address);
        this.productsOffered = new ArrayList<>();
        this.ordersSold = new ArrayList<>();
    }

    public ArrayList<Order> getOrdersSold() {
        return ordersSold;
    }

    public ArrayList<Product> getProductsOffered() {
        return productsOffered;
    }

    public void removeProductOffered(Product product) throws IllegalArgumentException {
        for (Product match : productsOffered) {
            if (match == product) {
                productsOffered.remove(match);
                return;
            }
        }
        throw new IllegalArgumentException("This product is not sold by this vendor");
    }

    public void addProductOffered(Product product) throws IllegalArgumentException {
        for (Product match : productsOffered) {
            if (match == product) {
                throw new IllegalArgumentException("This product is already sold by this vendor");
            }
        }
        productsOffered.add(product);
    }

    public void removeOrderSold(Order order) throws IllegalArgumentException {
        for (Order match : ordersSold) {
            if (match == order) {
                ordersSold.remove(match);
                return;
            }
        }
        throw new IllegalArgumentException("This order was not sold by this vendor");
    }

    public void addOrderSold(Order order) throws IllegalArgumentException {
        for (Order match : ordersSold) {
            if (match == order) {
                throw new IllegalArgumentException("This order was already sold by this vendor");
            }
        }
        ordersSold.add(order);
    }

    public SellerMetrics getMetrics() {
        // Compute revenue and number of products sold.
        int revenue = 0;
        int productsSold = 0;
        for (Order order : ordersSold) {
            for (Tuple<Product, Integer> tuple : order.getProducts()) {
                Product product = tuple.first;
                // Verify that product in order was sold by this seller
                if (product.getSeller().equals(this)) {
                    // Update revenue
                    revenue += product.getPrice() * tuple.second;
                    // Update number of products sold
                    productsSold += tuple.second;
                }
            }
        }
        // TODO add averageProductRating
        return new SellerMetrics(revenue, productsSold, this.productsOffered.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(getName(), seller.getName()) || Objects.equals(getId(), seller.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
