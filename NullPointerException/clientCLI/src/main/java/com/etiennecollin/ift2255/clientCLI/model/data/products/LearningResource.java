/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.time.LocalDate;
import java.util.UUID;

public class LearningResource extends Product {
    private String isbn;
    private String organisation;
    private LocalDate releaseDate;
    private int editionNumber;

    public LearningResource(int price, int quantity, String title, String description, UUID sellerId, int fidelityPoints, String isbn, String organisation, LocalDate releaseDate, LearningResourceType type, int editionNumber) throws IllegalArgumentException {
        super(price, quantity, title, description, ProductCategory.LearningResource, type, sellerId, fidelityPoints);
        this.isbn = isbn;
        this.organisation = organisation;
        this.releaseDate = releaseDate;
        this.editionNumber = editionNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
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
