/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Seller extends User {
    private final ArrayList<Product> productsOffered;
    private final ArrayList<Order> ordersSold;
    private final ArrayList<Buyer> followedBy;
    private String name; // Unique

    public Seller(String name, String email, String phoneNumber, String address, String password) {
        super(email, password, phoneNumber, address);
        this.setName(name);
        this.productsOffered = new ArrayList<>();
        this.ordersSold = new ArrayList<>();
        this.followedBy = new ArrayList<>();
    }

    public ArrayList<Buyer> getFollowedBy() {
        return followedBy;
    }

    public ArrayList<Order> getOrdersSold() {
        return ordersSold;
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

        // Send notification to buyers who follow this seller
        String title = "New product added by followed seller";
        String content = "Seller: " + this.getName() + "\nNew Product: " + product.getTitle() + "\nPrice: " + product.getCost();
        Notification notification = new Notification(title, content);
        for (Buyer buyer : followedBy) {
            buyer.addNotification(notification);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void toggleFollowedBy(Buyer buyer) {
        if (followedBy.contains(buyer)) {
            followedBy.remove(buyer);
        } else {
            followedBy.add(buyer);
        }
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

    public SellerMetrics getMetrics(int lastNMonths) {
        LocalDate dateCutOff = LocalDate.now().minusMonths(lastNMonths);

        // Compute recentRevenue and number of products sold.
        int recentRevenue = 0;
        int totalRevenue = 0;
        int numberTotalProductsSold = 0;
        int numberRecentProductsSold = 0;
        for (Order order : ordersSold) {
            if (order.getOrderDate().isAfter(dateCutOff)) {
                recentRevenue += order.getCost();
                numberRecentProductsSold += order.getNumberOfProducts();
            }
            totalRevenue += order.getCost();
            numberTotalProductsSold += order.getNumberOfProducts();
        }

        // Compute average ratings
        int numberRecentRatings = 0;
        int sumRecentRatings = 0;
        int sumTotalRatings = 0;
        int averageRecentRatings = 0;
        int averageTotalRatings = 0;

        try {
            for (Product product : productsOffered) {
                sumTotalRatings += product.getRating().getRating();
                if (product.getCommercializationDate().isAfter(dateCutOff)) {
                    sumRecentRatings += product.getRating().getRating();
                    numberRecentRatings++;
                }
            }
            if (numberRecentRatings != 0) {
                averageRecentRatings = sumRecentRatings / numberRecentRatings;
            }

            if (!this.getProductsOffered().isEmpty()) {
                averageTotalRatings = sumTotalRatings / this.getProductsOffered().size();
            }
        } catch (NoSuchElementException e) {
            averageRecentRatings = -1;
            averageTotalRatings = -1;
        }

        return new SellerMetrics(recentRevenue, totalRevenue, numberRecentProductsSold, numberTotalProductsSold, this.getProductsOffered().size(), averageRecentRatings, averageTotalRatings);
    }

    public ArrayList<Product> getProductsOffered() {
        return productsOffered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(getName(), seller.getName()) || Objects.equals(getId(), seller.getId());
    }
}
