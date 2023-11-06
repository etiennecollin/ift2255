/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private final UUID id;
    private int price;
    private Category category;
    private String title; // Unique
    private int likes;
    private Seller seller;
    private ArrayList<Comment> comments;
    private Rating rating;
    private int fidelityPoints;

    public Product(int price, String title, Category category, int fidelityPoints) {
        this.setPrice(price);
        this.setTitle(title);
        this.setCategory(category);
        this.setFidelityPoints(fidelityPoints);
        this.setLikes(0);
        this.setComments(new ArrayList<>());
        this.setRating(new Rating());
        this.id = UUID.randomUUID();
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
