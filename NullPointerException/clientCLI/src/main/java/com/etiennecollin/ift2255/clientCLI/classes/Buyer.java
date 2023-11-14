/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Buyer extends User {
    private final Cart cart;
    private final ArrayList<Product> productsLiked;
    private final ArrayList<Comment> commentsLiked;
    private final ArrayList<Comment> commentsWritten;
    private final ArrayList<Order> orders;
    private String lastName;
    private String firstName;
    private String username; // Unique
    private int fidelityPoints = 0;

    public Buyer(String lastName, String firstName, String username, String password) {
        super(password);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setUsername(username);
        this.cart = new Cart();
        this.productsLiked = new ArrayList<>();
        this.commentsLiked = new ArrayList<>();
        this.commentsWritten = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void createTicket(Order order, String description) {
        if (!this.orders.contains(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }

        // Check if order can still be reported
        if (LocalDate.now().isAfter(order.getOrderDate().plusYears(1))) {
            throw new IllegalArgumentException("This order can no longer be reported");
        }

        for (Tuple<Product, Integer> match : order.getProducts()) {
            Product product = match.first;
            new Ticket(description, product, this, product.getSeller());
        }
    }

    public void createTicket(Order order, Product product, String description) {
        if (!this.orders.contains(order)) {
            throw new IllegalArgumentException("This order does not belong to the current user");
        }

        // Check if order can still be reported
        if (LocalDate.now().isAfter(order.getOrderDate().plusYears(1))) {
            throw new IllegalArgumentException("This order can no longer be reported");
        }

        // Check if product is in order
        boolean isFound = false;
        for (Tuple<Product, Integer> match : order.getProducts()) {
            if (match.first == product) {
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new IllegalArgumentException("This product is not in this order");
        }

        new Ticket(description, product, this, product.getSeller());
    }

    public ArrayList<Comment> getCommentsWritten() {
        return commentsWritten;
    }

    public void addCommentWritten(Comment comment) {
        this.commentsWritten.add(comment);
    }

    public void removeCommentWritten(Comment comment) {
        if (!this.commentsWritten.remove(comment)) {
            throw new IllegalArgumentException("This comment does not belong to the current user");
        }
        comment.delete();
    }

    public Cart getCart() {
        return cart;
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

    public boolean doesLike(Comment comment) {
        return commentsLiked.contains(comment);
    }

    public ArrayList<Comment> getCommentsLiked() {
        return commentsLiked;
    }

    public void toggleLike(Product product) {
        if (productsLiked.contains(product)) {
            productsLiked.remove(product);
        } else {
            productsLiked.add(product);
        }
    }

    public void toggleLike(Comment comment) {
        if (commentsLiked.contains(comment)) {
            commentsLiked.remove(comment);
        } else {
            commentsLiked.add(comment);
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}
