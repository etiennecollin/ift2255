/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data.products;

import com.etiennecollin.ift2255.clientCLI.models.data.DatabaseObject;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * An abstract class representing a product in the system.
 */
public abstract class Product extends DatabaseObject {
    //    private static final int MAX_PTS_PER_DOLLAR = 20;
    /**
     * The unique identifier of the seller associated with the product.
     */
    private final UUID sellerId;
    /**
     * The date when the product was commercialized.
     */
    private final LocalDate commercializationDate;
    /**
     * The category of the product.
     */
    private final ProductCategory category;
    /**
     * The sub-category of the product.
     */
    private final Enum<?> subCategory;
    /**
     * The rating of the product.
     */
    private int rating;
    /**
     * The number of likes received by the product.
     */
    private int likes;
    /**
     * The price of the product.
     */
    private int price;
    /**
     * The available quantity of the product.
     */
    private int quantity;
    /**
     * The title of the product (unique).
     */
    private String title;
    /**
     * The description of the product.
     */
    private String description;
    /**
     * The bonus fidelity points associated with the product.
     */
    private int bonusFidelityPoints;
    /**
     * The discount applied to the product as part of a promotion.
     */
    private int promoDiscount;
    /**
     * The fidelity points awarded as part of a promotion.
     */
    private int promoFidelityPoints;
    /**
     * The end date of the promotion.
     */
    private LocalDate promoEndDate;

    /**
     * Constructs a Product with the specified parameters.
     *
     * @param price               The price of the product.
     * @param quantity            The available quantity of the product.
     * @param title               The title of the product.
     * @param description         The description of the product.
     * @param category            The category of the product.
     * @param subCategory         The sub-category of the product.
     * @param sellerId            The unique identifier of the seller associated with the product.
     * @param bonusFidelityPoints The bonus fidelity points associated with the product.
     */
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

    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price The price to set for the product.
     */
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

    /**
     * Gets the sub-category of the product.
     *
     * @return The sub-category of the product.
     */
    public Enum<?> getSubCategory() {
        return subCategory;
    }

    /**
     * Gets the likes received by the product.
     *
     * @return The number of likes received by the product.
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Sets the number of likes received by the product.
     *
     * @param likes The number of likes to set for the product.
     */
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

    /**
     * Gets the discount percentage applied during a promotion.
     *
     * @return The discount percentage.
     */
    public int getPromoDiscount() {
        return promoDiscount;
    }

    /**
     * Sets the discount percentage for a promotion.
     *
     * @param promoDiscount The discount percentage to set.
     */
    public void setPromoDiscount(int promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    /**
     * Gets the fidelity points awarded during a promotion.
     *
     * @return The fidelity points awarded.
     */
    public int getPromoFidelityPoints() {
        return promoFidelityPoints;
    }

    /**
     * Sets the fidelity points awarded for a promotion.
     *
     * @param promoFidelityPoints The fidelity points to set.
     */
    public void setPromoFidelityPoints(int promoFidelityPoints) {
        this.promoFidelityPoints = promoFidelityPoints;
    }

    /**
     * Gets the end date of the promotion.
     *
     * @return The end date of the promotion.
     */
    public LocalDate getPromoEndDate() {
        return promoEndDate;
    }

    /**
     * Sets the end date for a promotion.
     *
     * @param promoEndDate The end date to set.
     */
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

    /**
     * Gets the category of the product.
     *
     * @return The category of the product.
     */
    public ProductCategory getCategory() {
        return category;
    }

    //    public ArrayList<Buyer> getFollowedBy() {
    //        return followedBy;
    //    }

    /**
     * Gets the quantity of the product available.
     *
     * @return The quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity The quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the description of the product.
     *
     * @return The description of the product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the commercialization date of the product.
     *
     * @return The commercialization date of the product.
     */
    public LocalDate getCommercializationDate() {
        return commercializationDate;
    }

    /**
     * Gets the bonus fidelity points awarded for purchasing the product.
     *
     * @return The bonus fidelity points.
     */
    public int getBonusFidelityPoints() {
        return bonusFidelityPoints;
    }

    /**
     * Sets the bonus fidelity points awarded for purchasing the product.
     *
     * @param bonusFidelityPoints The bonus fidelity points to set.
     *
     * @throws IllegalArgumentException If the bonus fidelity points are less than 0 or exceed the allowed limit.
     */
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

    /**
     * Checks if this product is equal to another object.
     *
     * @param o The object to compare.
     *
     * @return {@code true} if the products are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && sellerId == product.getSellerId() && Objects.equals(getTitle(), product.getTitle());
    }

    /**
     * Gets the unique identifier of the seller associated with the product.
     *
     * @return The unique identifier of the seller associated with the product.
     */
    public UUID getSellerId() {
        return sellerId;
    }

    /**
     * Gets the title of the product.
     *
     * @return The title of the product.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the product.
     *
     * @param title The title to set for the product.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
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

    /**
     * Gets the rating of the product.
     *
     * @return The rating of the product.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the product.
     *
     * @param rating The rating to set for the product.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
