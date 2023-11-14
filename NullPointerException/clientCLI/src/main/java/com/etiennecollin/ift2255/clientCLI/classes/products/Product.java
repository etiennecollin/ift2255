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
    private final UUID id;
    private final LocalDate commercializationDate;
    private final ProductCategory productCategory;
    private int price;
    private int quantity;
    private String title; // Unique
    private String description;
    private int likes;
    private Seller seller;
    private ArrayList<Comment> comments;
    private Rating rating;
    private int fidelityPoints;

    public Product(int price, int quantity, String title, String description, ProductCategory productCategory, int fidelityPoints) {
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTitle(title);
        this.setDescription(description);
        this.productCategory = productCategory;
        this.setFidelityPoints(fidelityPoints);

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

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
