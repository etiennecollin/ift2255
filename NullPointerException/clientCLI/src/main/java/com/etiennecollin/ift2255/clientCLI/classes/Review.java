/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Review implements Serializable {
    public static final int POINTS_PER_REVIEW = 10;
    private final UUID id;
    private final LocalDate creationDate;
    private String content;
    private String title;
    private Buyer author;
    private Product product;
    private int rating;
    private int likes;
    private int reports;
    private boolean arePointsGiven;

    public Review(String content, String title, Buyer author, Product product, int rating) {
        this.setContent(content);
        this.setTitle(title);
        this.setAuthor(author);
        this.setProduct(product);
        this.setRating(rating);
        this.setLikes(0);
        this.creationDate = LocalDate.now();
        this.arePointsGiven = false;
        this.id = UUID.randomUUID();
    }

    private void updateAuthorPoints() {
        if ((this.reports > 0 || this.likes == 0) && this.arePointsGiven) {
            this.arePointsGiven = false;
            this.author.removeFidelityPoints(POINTS_PER_REVIEW);
        } else if (this.reports == 0 && this.likes > 0 && !this.arePointsGiven) {
            this.arePointsGiven = true;
            this.author.addFidelityPoints(POINTS_PER_REVIEW);
        }
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 0 || rating > 100) {
            throw new IllegalArgumentException("Rating must be between 0 and 100 inclusively");
        }
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void delete() {
        setLikes(0);
        setReports(0);
        updateAuthorPoints();
    }

    public boolean arePointsGiven() {
        return arePointsGiven;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(getId(), review.getId());
    }

    @Override
    public String toString() {
        return "Comment{" + "title='" + title + '\'' + ", content='" + content + '\'' + ", author=" + author + ", likes=" + likes + '}';
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Buyer getAuthor() {
        return author;
    }

    public void setAuthor(Buyer author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
        updateAuthorPoints();
    }

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
        updateAuthorPoints();
    }
}
