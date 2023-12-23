/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import com.etiennecollin.ift2255.clientCLI.classes.Seller;

import java.time.LocalDate;

public class LearningResource extends Product {
    private int isbn;
    private String organisation;
    private LocalDate releaseDate;
    private int editionNumber;

    public LearningResource(int price, int quantity, String title, String description, Seller seller, int fidelityPoints, int isbn, String organisation, LocalDate releaseDate, LearningResourceType type, int editionNumber) throws IllegalArgumentException {
        super(price, quantity, title, description, ProductCategory.LearningResource, type, seller, fidelityPoints);
        this.isbn = isbn;
        this.organisation = organisation;
        this.releaseDate = releaseDate;
        this.editionNumber = editionNumber;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }
}
