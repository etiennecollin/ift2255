/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import com.etiennecollin.ift2255.clientCLI.model.data.DatabaseObject;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class Product extends DatabaseObject {
    //    private static final int MAX_PTS_PER_DOLLAR = 20;
    private final UUID sellerId;
    private final LocalDate commercializationDate;
    private final ProductCategory category;
    private final Enum<?> subCategory;
    private int rating;
    private int likes;
    private int price;
    private int quantity;
    private String title; // Unique
    private String description;
    private int bonusFidelityPoints;
    private int promoDiscount;
    private int promoFidelityPoints;
    private LocalDate promoEndDate;

    public Product(int price, int quantity, String title, String description, ProductCategory category, Enum<?> subCategory, UUID sellerId, int bonusFidelityPoints) {
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTitle(title);
        this.setDescription(description);
        this.category = category;
        this.subCategory = subCategory;
        this.sellerId = sellerId;
        this.setBonusFidelityPoints(bonusFidelityPoints);

        this.commercializationDate = LocalDate.now();

        this.rating = 0;
    }

    //    public void setReview(ArrayList<Review> reviews) {
    //        this.reviews = reviews;
    //    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //    public String getFormattedCost() {
    //        return getCost() / 100 + "." + getCost() % 100 + "$";
    //    }
    //
    //    public String getFormattedCost(int quantity) {
    //        return getCost() * quantity / 100 + "." + getCost() * quantity % 100 + "$";
    //    }

    public Enum<?> getSubCategory() {
        return subCategory;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    //    public void toggleFollowedBy(Buyer buyer) {
    //        if (followedBy.contains(buyer)) {
    //            followedBy.remove(buyer);
    //        } else {
    //            followedBy.add(buyer);
    //        }
    //    }

    public int getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(int promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public int getPromoFidelityPoints() {
        return promoFidelityPoints;
    }

    public void setPromoFidelityPoints(int promoFidelityPoints) {
        this.promoFidelityPoints = promoFidelityPoints;
    }

    public LocalDate getPromoEndDate() {
        return promoEndDate;
    }

    public void setPromoEndDate(LocalDate promoEndDate) {
        this.promoEndDate = promoEndDate;
    }
    //      public void setDiscount(int discount) throws IllegalArgumentException {
    //        if (discount < 0 || discount > 100) {
    //            throw new IllegalArgumentException("The discount should be a percentage between 0% and 100%");
    //        }
    //
    //        this.discount = discount;
    //
    //        // Send notification to buyers who follow this seller
    //        String title = "New promotion added on a product sold by followed seller";
    //        String content = "Seller: " + this.seller.getName() + "\nProduct: " + this.getTitle() + "\nPrice: " + this.getCost() + "\nPromotion: " + this.discount + "%";
    //        Notification notification = new Notification(title, content);
    //
    //        // Prevent sending duplicate of notifications
    //        HashSet<Buyer> sendTo = new HashSet<>();
    //        sendTo.addAll(this.seller.getFollowedBy()); // Send to buyers who follow the seller
    //        sendTo.addAll(this.getFollowedBy()); // Send to buyers who follow the product
    //        this.getFollowedBy().forEach(buyer -> sendTo.addAll(buyer.getFollowedBy())); // Send to buyers who follow a
    //        // buyer who
    //        // follows this product
    //
    //        for (Buyer buyer : sendTo) {
    //            buyer.addNotification(notification);
    //        }
    //    }

    public ProductCategory getCategory() {
        return category;
    }

    //    public ArrayList<Buyer> getFollowedBy() {
    //        return followedBy;
    //    }

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

    public void setBonusFidelityPoints(int bonusFidelityPoints) throws IllegalArgumentException {
        //        if (bonusFidelityPoints < 0) {
        //            this.bonusFidelityPoints = 0;
        //            throw new IllegalArgumentException("Cannot give less than 0 bonus points for a product");
        //        }
        //
        //        float bonusPointsPerDollar = (float) (bonusFidelityPoints) / ((float) this.getCost() / 100);
        //        if (bonusPointsPerDollar > MAX_PTS_PER_DOLLAR - 1) {
        //            this.bonusFidelityPoints = ((MAX_PTS_PER_DOLLAR - 1) * this.getCost()) / 100;
        //            throw new IllegalArgumentException("Products cannot provide more than " + (MAX_PTS_PER_DOLLAR - 1) + " bonus points per dollar spent. Bonus points were clamped to match" + " this maximum.");
        //        } else {
        //            this.bonusFidelityPoints = bonusFidelityPoints;
        //        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && sellerId == product.getSellerId() && Objects.equals(getTitle(), product.getTitle());
    }

    @Override
    public String toString() {
        return title;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //    public ArrayList<Review> getReviews() {
    //        return reviews;
    //    }
    //
    //    public void addReview(Review review) {
    //        this.reviews.add(review);
    //        this.rating.addRating(review.getRating());
    //        review.getAuthor().addReviewWritten(review);
    //
    //        String title = "New review on one of your products";
    //        String content = "Product: " + this.getTitle() + "\nReview Title: " + review.getTitle() + "\nReview body: " + review.getContent();
    //        Notification notification = new Notification(title, content);
    //        this.seller.addNotification(notification);
    //    }
    //
    //    public void removeReview(Review review) {
    //        if (review.arePointsGiven()) {
    //            review.getAuthor().removeFidelityPoints(Review.POINTS_PER_REVIEW);
    //        }
    //
    //        this.reviews.remove(review);
    //        this.rating.removeRating(review.getRating());
    //    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
