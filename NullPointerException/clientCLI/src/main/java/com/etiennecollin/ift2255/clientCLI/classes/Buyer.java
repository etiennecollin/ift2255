/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.time.LocalDate;
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

    public Buyer(String firstName, String lastName, String username, String email, String phoneNumber, String address, String password) {
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
        addFidelityPoints(order.getNumberOfFidelityPoints());
    }

    public void addFidelityPoints(int fidelityPoints) {
        this.fidelityPoints += fidelityPoints;
    }

    public void returnOrder(Order order) {
        if (!this.orders.remove(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }
        removeFidelityPoints(order.getNumberOfFidelityPoints());
    }

    public void removeFidelityPoints(int fidelityPoints) {
        this.fidelityPoints -= fidelityPoints;
    }

    public void exchangeOrder(Order order) {
        if (!this.orders.remove(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }
        removeFidelityPoints(order.getNumberOfFidelityPoints());
    }

    public void cancelOrder(Order order) {
        if (!this.orders.remove(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }
        removeFidelityPoints(order.getNumberOfFidelityPoints());
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

    public boolean toggleLike(Product product) {
        if (productsLiked.contains(product)) {
            productsLiked.remove(product);
            product.toggleFollowedBy(this);
            return false;
        } else {
            productsLiked.add(product);
            product.toggleFollowedBy(this);
            return true;
        }
    }

    public void toggleLike(Buyer buyer) {
        if (buyersLiked.contains(buyer)) {
            buyersLiked.remove(buyer);

            // Remove points
            buyer.removeFidelityPoints(5);
            this.removeFidelityPoints(5);
        } else {
            buyersLiked.add(buyer);

            // Add points
            buyer.addFidelityPoints(5);
            this.addFidelityPoints(5);

            // Send notification
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

    public BuyerMetrics getMetrics(int lastNMonths) {
        LocalDate dateCutOff = LocalDate.now().minusMonths(lastNMonths);

        // Compute recentRevenue and number of products sold.
        int numberRecentProductsBought = 0;
        int numberTotalProductsBought = 0;
        int numberRecentOrders = 0;
        int numberTotalOrders = 0;
        for (Order order : orders) {
            if (order.getOrderDate().isAfter(dateCutOff)) {
                numberRecentProductsBought += order.getNumberOfProducts();
                numberRecentOrders++;
            }
            numberTotalProductsBought += order.getNumberOfProducts();
            numberTotalOrders++;
        }

        int averageRecentReviews = 0;
        int averageTotalReviews = 0;
        int numberRecentReviews = 0;
        int numberTotalReviews = 0;
        for (Review review : reviewsWritten) {
            if (review.getCreationDate().isAfter(dateCutOff)) {
                averageRecentReviews += review.getRating();
                numberRecentReviews++;
            }
            averageTotalReviews += review.getRating();
            numberTotalReviews++;
        }
        averageRecentReviews = averageRecentReviews / numberRecentReviews;
        averageTotalReviews = averageTotalReviews / numberTotalReviews;
        return new BuyerMetrics(numberRecentOrders, numberTotalOrders, numberRecentProductsBought, numberTotalProductsBought, this.getFollowedBy().size(), averageRecentReviews, averageTotalReviews, numberRecentReviews, numberTotalReviews);
    }

    public ArrayList<Buyer> getFollowedBy() {
        return followedBy;
    }
}
