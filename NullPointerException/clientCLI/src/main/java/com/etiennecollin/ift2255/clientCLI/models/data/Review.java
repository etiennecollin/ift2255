/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a review in the system, extending the DatabaseObject class.
 */
public class Review extends DatabaseObject {
    /**
     * The unique identifier of the author of the review.
     */
    private final UUID authorId;
    /**
     * The unique identifier of the product being reviewed.
     */
    private final UUID productId;
    /**
     * The date when the review was created.
     */
    private final LocalDate creationDate;
    /**
     * The comment provided in the review.
     */
    private String comment;
    /**
     * The rating given in the review.
     */
    private int rating;
    /**
     * The number of likes received by the review.
     */
    private int likes;
    /**
     * A flag indicating whether the review has been reported.
     */
    private boolean isReported;
    /**
     * The unique identifier of the user who reported the review.
     */
    private UUID reportedBy;

    /**
     * Constructs a Review object with the given parameters.
     *
     * @param authorId     The unique identifier of the author of the review.
     * @param productId    The unique identifier of the product being reviewed.
     * @param comment      The comment provided in the review.
     * @param rating       The rating given in the review.
     * @param creationDate The date when the review was created.
     */
    public Review(UUID authorId, UUID productId, String comment, int rating, LocalDate creationDate) {
        this.authorId = authorId;
        this.productId = productId;
        this.comment = comment;
        this.rating = rating;
        this.creationDate = creationDate;
        this.likes = 0;
        this.isReported = false;
        this.reportedBy = null;
    }

    /**
     * Gets the unique identifier of the author of the review.
     *
     * @return The unique identifier of the author.
     */
    public UUID getAuthorId() {
        return authorId;
    }

    /**
     * Gets the unique identifier of the product being reviewed.
     *
     * @return The unique identifier of the product.
     */
    public UUID getProductId() {
        return productId;
    }

    /**
     * Gets the date when the review was created.
     *
     * @return The creation date of the review.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the comment provided in the review.
     *
     * @return The comment in the review.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment in the review.
     *
     * @param comment The new comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the rating given in the review.
     *
     * @return The rating in the review.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating in the review.
     *
     * @param rating The new rating to set.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets the number of likes received by the review.
     *
     * @return The number of likes.
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Sets the number of likes for the review.
     *
     * @param likes The new number of likes to set.
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Checks if the review has been reported.
     *
     * @return {@code true} if the review has been reported, {@code false} otherwise.
     */
    public boolean getIsReported() {
        return isReported;
    }

    /**
     * Sets the reported status of the review.
     *
     * @param isReported The new reported status to set.
     */
    public void setIsReported(boolean isReported) {
        this.isReported = isReported;
    }

    /**
     * Gets the unique identifier of the user who reported the review.
     *
     * @return The unique identifier of the reporting user.
     */
    public UUID getReportedBy() {
        return reportedBy;
    }

    /**
     * Sets the unique identifier of the user who reported the review.
     *
     * @param reportedBy The unique identifier of the reporting user.
     */
    public void setReportedBy(UUID reportedBy) {
        this.reportedBy = reportedBy;
    }
}
