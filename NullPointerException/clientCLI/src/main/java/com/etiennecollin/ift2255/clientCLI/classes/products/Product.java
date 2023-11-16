/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import com.etiennecollin.ift2255.clientCLI.classes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public abstract class Product {
    private final static int MAX_PTS_PER_DOLLAR = 20;
    private final UUID id;
    private final LocalDate commercializationDate;
    private final ProductCategory productCategory;
    private final ArrayList<Buyer> followedBy;
    private final Rating rating;
    private int cost;
    private int quantity;
    private String title; // Unique
    private String description;
    private int likes;
    private Seller seller;
    private ArrayList<Review> reviews;
    private int bonusFidelityPoints;
    private int rebate;

    public Product(int cost, int quantity, String title, String description, ProductCategory productCategory) {
        this.setCost(cost);
        this.setQuantity(quantity);
        this.setTitle(title);
        this.setDescription(description);
        this.productCategory = productCategory;
        this.setBonusFidelityPoints(0);

        this.commercializationDate = LocalDate.now();
        this.rebate = 0;
        this.setLikes(0);
        this.setReview(new ArrayList<>());
        this.rating = new Rating();
        this.followedBy = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    public void setReview(ArrayList<Review> reviews) {
        this.reviews = reviews;
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
        this.setReview(new ArrayList<>());
        this.rating = new Rating();
        this.followedBy = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    public void toggleFollowedBy(Buyer buyer) {
        if (followedBy.contains(buyer)) {
            followedBy.remove(buyer);
        } else {
            followedBy.add(buyer);
        }
    }

    public int getRebate() {
        return rebate;
    }

    public void setRebate(int rebate) {
        if (rebate < 0 || rebate > 100) {
            throw new IllegalArgumentException("The rebate should be a percentage between 0% and 100%");
        }

        this.rebate = rebate;

        // Send notification to buyers who follow this seller
        String title = "New promotion added on a product sold by followed seller";
        String content = "Seller: " + this.seller.getName() + "\nProduct: " + this.getTitle() + "\nPrice: " + this.getCost() + "\nPromotion: " + this.rebate + "%";
        Notification notification = new Notification(title, content);

        // Prevent sending duplicate of notifications
        HashSet<Buyer> sendTo = new HashSet<>();
        sendTo.addAll(this.seller.getFollowedBy()); // Send to buyers who follow the seller
        sendTo.addAll(this.getFollowedBy()); // Send to buyers who follow the product
        this.getFollowedBy().forEach(buyer -> sendTo.addAll(buyer.getFollowedBy())); // Send to buyers who follow a buyer who follows this product

        for (Buyer buyer : sendTo) {
            buyer.addNotification(notification);
        }
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Buyer> getFollowedBy() {
        return followedBy;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        this.rating.addRating(review.getRating());
        review.getAuthor().addReviewWritten(review);

        String title = "New review on one of your products";
        String content = "Product: " + this.getTitle() + "\nReview Title: " + review.getTitle() + "\nReview body: " + review.getContent();
        Notification notification = new Notification(title, content);
        this.seller.addNotification(notification);
    }

    public void removeReview(Review review) {
        if (review.arePointsGiven()) {
            review.getAuthor().removeFidelityPoints(Review.POINTS_PER_REVIEW);
        }

        this.reviews.remove(review);
        this.rating.removeRating(review.getRating());
    }

    public Rating getRating() {
        return rating;
    }
}
