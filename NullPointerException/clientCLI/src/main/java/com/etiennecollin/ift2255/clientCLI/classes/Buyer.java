/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.util.ArrayList;
import java.util.Objects;

public class Buyer extends User {
    private String lastName;
    private String firstName;
    private String username; // Unique
    private ArrayList<Product> productsLiked;
    private ArrayList<Comment> commentsLiked;

    public Buyer(String lastName, String firstName, String username) {
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setUsername(username);
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
        return Objects.equals(getUsername(), buyer.getUsername()) && Objects.equals(getId(), buyer.getId());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
