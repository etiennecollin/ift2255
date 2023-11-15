/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import com.etiennecollin.ift2255.clientCLI.classes.Comment;
import com.etiennecollin.ift2255.clientCLI.classes.Rating;
import com.etiennecollin.ift2255.clientCLI.classes.Seller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public abstract class Product {
    private final static int MAX_PTS_PER_DOLLAR = 20;
    private final UUID id;
    private final LocalDate commercializationDate;
    private final ProductCategory productCategory;
    private int cost;
    private int quantity;
    private String title; // Unique
    private String description;
    private int likes;
    private Seller seller;
    private ArrayList<Comment> comments;
    private Rating rating;
    private int bonusFidelityPoints;

    public Product(int cost, int quantity, String title, String description, ProductCategory productCategory) {
        this.setCost(cost);
        this.setQuantity(quantity);
        this.setTitle(title);
        this.setDescription(description);
        this.productCategory = productCategory;
        this.setBonusFidelityPoints(0);

        this.commercializationDate = LocalDate.now();
        this.setLikes(0);
        this.setComments(new ArrayList<>());
        this.setRating(new Rating());
        this.id = UUID.randomUUID();
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Product(int cost, int quantity, String title, String description, ProductCategory productCategory, int bonusFidelityPoints) {
        this.setCost(cost);
        this.setQuantity(quantity);
        this.setTitle(title);
        this.setDescription(description);
        this.productCategory = productCategory;
        this.setBonusFidelityPoints(bonusFidelityPoints);

        this.commercializationDate = LocalDate.now();
        this.setLikes(0);
        this.setComments(new ArrayList<>());
        this.setRating(new Rating());
        this.id = UUID.randomUUID();
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCommercializationDate() {
        return commercializationDate;
    }

    public int getBonusFidelityPoints() {
        return bonusFidelityPoints;
    }

    public void setBonusFidelityPoints(int bonusFidelityPoints) {
        if (bonusFidelityPoints < 0) {
            throw new IllegalArgumentException("Cannot give less than 0 bonus points for a product");
        }

        int newPointsPerDollar = (1 + bonusFidelityPoints) / (this.getCost() / 100);
        if (newPointsPerDollar > MAX_PTS_PER_DOLLAR) {
            this.bonusFidelityPoints = (MAX_PTS_PER_DOLLAR * this.getCost()) / 100 - 1;
            throw new IllegalArgumentException("Products cannot provide more than " + MAX_PTS_PER_DOLLAR + " bonus points per dollar spent. Bonus points were clamped to match this maximum.");
        } else {
            this.bonusFidelityPoints = bonusFidelityPoints;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getSeller(), product.getSeller()) && Objects.equals(getTitle(), product.getTitle());
    }

    public UUID getId() {
        return id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductCategory getCategory() {
        return productCategory;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        if (likes < 0) {
            throw new IllegalArgumentException("Cannot have a negative number of likes");
        }
        this.likes = likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        if (comment.arePointsGiven()) {
            comment.getAuthor().removeFidelityPoints(Comment.POINTS_PER_REVIEW);
        }
        this.comments.remove(comment);
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
