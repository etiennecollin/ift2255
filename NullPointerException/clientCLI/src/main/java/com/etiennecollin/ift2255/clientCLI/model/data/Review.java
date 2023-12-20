/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.time.LocalDate;
import java.util.UUID;

public class Review extends DatabaseObject {
    private final UUID authorId;
    private final UUID productId;
    private final LocalDate creationDate;
    private String comment;
    private int rating;
    private int likes;
    private boolean isReported;
    private UUID reportedBy;

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

    public UUID getAuthorId() {
        return authorId;
    }

    public UUID getProductId() {
        return productId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean getIsReported() {
        return isReported;
    }

    public void setIsReported(boolean isReported) {
        this.isReported = isReported;
    }

    public UUID getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(UUID reportedBy) {
        this.reportedBy = reportedBy;
    }
}
