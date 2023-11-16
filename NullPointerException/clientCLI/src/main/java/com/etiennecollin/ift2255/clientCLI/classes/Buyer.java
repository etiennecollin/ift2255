/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.util.ArrayList;
import java.util.Objects;

public class Buyer extends User {
    private final ArrayList<Product> productsLiked;
    private final ArrayList<Review> commentsLiked;
    private final ArrayList<Buyer> buyersLiked;
    private final ArrayList<Seller> sellersLiked;
    private final ArrayList<Review> reviewsWritten;
    private final ArrayList<Buyer> followedBy;
    private final ArrayList<Order> orders;
    private Cart cart;
    private String lastName;
    private String firstName;
    private String username; // Unique
    private int fidelityPoints = 0;

    public Buyer(String firstName, String lastName, String username, String email, int phoneNumber, String address, String password) {
        super(email, password, phoneNumber, address);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUsername(username);
        this.cart = new Cart(this);
        this.productsLiked = new ArrayList<>();
        this.commentsLiked = new ArrayList<>();
        this.sellersLiked = new ArrayList<>();
        this.buyersLiked = new ArrayList<>();
        this.followedBy = new ArrayList<>();
        this.reviewsWritten = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public ArrayList<Buyer> getFollowedBy() {
        return followedBy;
    }

    public ArrayList<Buyer> getBuyersLiked() {
        return buyersLiked;
    }

    public ArrayList<Seller> getSellersLiked() {
        return sellersLiked;
    }

    public ArrayList<Review> getReviewsWritten() {
        return reviewsWritten;
    }

    public void addReviewWritten(Review review) {
        this.reviewsWritten.add(review);
    }

    public void removeReviewWritten(Review review) {
        if (!this.reviewsWritten.remove(review)) {
            throw new IllegalArgumentException("This comment does not belong to the current user");
        }
        review.delete();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        if (!cart.getBuyer().equals(this)) {
            throw new IllegalArgumentException("This cart does not belong to this buyer");
        }
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(getUsername(), buyer.getUsername()) || Objects.equals(getId(), buyer.getId());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        addFidelityPoints(order.getFidelityPoints());
    }

    public void addFidelityPoints(int fidelityPoints) {
        this.fidelityPoints += fidelityPoints;
    }

    public void returnOrder(Order order) {
        if (!this.orders.remove(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }
        removeFidelityPoints(order.getFidelityPoints());
    }

    public void removeFidelityPoints(int fidelityPoints) {
        this.fidelityPoints -= fidelityPoints;
    }

    public void exchangeOrder(Order order) {
        if (!this.orders.remove(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }
        removeFidelityPoints(order.getFidelityPoints());
    }

    public void cancelOrder(Order order) {
        if (!this.orders.remove(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }
        removeFidelityPoints(order.getFidelityPoints());
    }

    public void orderDelivered(Order order) {
        if (!this.orders.contains(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }
        order.setDelivered();
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public ArrayList<Product> getProductsLiked() {
        return productsLiked;
    }

    public boolean doesLike(Product product) {
        return productsLiked.contains(product);
    }

    public boolean doesLike(Review review) {
        return commentsLiked.contains(review);
    }

    public boolean doesLike(Seller seller) {
        return sellersLiked.contains(seller);
    }

    public boolean doesLike(Buyer buyer) {
        return buyersLiked.contains(buyer);
    }

    public ArrayList<Review> getCommentsLiked() {
        return commentsLiked;
    }

    public void toggleLike(Product product) {
        if (productsLiked.contains(product)) {
            productsLiked.remove(product);
        } else {
            productsLiked.add(product);
        }
        product.toggleFollowedBy(this);
    }

    public void toggleLike(Buyer buyer) {
        if (buyersLiked.contains(buyer)) {
            buyersLiked.remove(buyer);
        } else {
            buyersLiked.add(buyer);

            String title = "New follower";
            String content = "You are now followed by: " + this.getFirstName() + " " + this.getLastName();
            Notification notification = new Notification(title, content);
            buyer.addNotification(notification);
        }
        buyer.toggleFollowedBy(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void toggleFollowedBy(Buyer buyer) {
        if (followedBy.contains(buyer)) {
            followedBy.remove(buyer);
        } else {
            followedBy.add(buyer);
        }
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void toggleLike(Seller seller) {
        if (sellersLiked.contains(seller)) {
            sellersLiked.remove(seller);
        } else {
            sellersLiked.add(seller);

            String title = "New follower";
            String content = "You are now followed by: " + this.getFirstName() + " " + this.getLastName();
            Notification notification = new Notification(title, content);
            seller.addNotification(notification);
        }
        seller.toggleFollowedBy(this);
    }

    public void toggleLike(Review review) {
        if (commentsLiked.contains(review)) {
            commentsLiked.remove(review);
        } else {
            commentsLiked.add(review);
        }
    }

    public BuyerMetrics getMetrics() {
        // Compute revenue and number of products sold.
        int numberOfProductsBought = 0;
        for (Order order : orders) {
            for (Tuple<Product, Integer> tuple : order.getProducts()) {
                numberOfProductsBought += tuple.second;
            }
        }

        int averageReview = 0;
        for (Review review : reviewsWritten) {
            averageReview += review.getRating();
        }
        averageReview = averageReview / reviewsWritten.size();

        return new BuyerMetrics(this.orders.size(), numberOfProductsBought, this.followedBy.size(), averageReview, reviewsWritten.size());
    }
}
