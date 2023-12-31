/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data.products;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a learning resource, such as a book or manual, with additional attributes specific to learning resources.
 * <p>
 * This class extends the general {@link Product} class and includes attributes such as ISBN, organization, release date,
 * and edition number.
 */
public class LearningResource extends Product {
    /**
     * The ISBN (International Standard Book Number) associated with the learning resource.
     */
    private String isbn;
    /**
     * The organization or publisher of the learning resource.
     */
    private String organisation;
    /**
     * The release date of the learning resource.
     */
    private LocalDate releaseDate;
    /**
     * The edition number of the learning resource.
     */
    private int editionNumber;

    /**
     * Constructs a LearningResource with specified attributes.
     *
     * @param price          The price of the learning resource.
     * @param quantity       The quantity available.
     * @param title          The title of the learning resource.
     * @param description    The description of the learning resource.
     * @param sellerId       The UUID of the seller offering the learning resource.
     * @param fidelityPoints The fidelity points associated with the learning resource.
     * @param isbn           The ISBN of the learning resource.
     * @param organisation   The organization or publisher of the learning resource.
     * @param releaseDate    The release date of the learning resource.
     * @param type           The type of learning resource (printed or electronic).
     * @param editionNumber  The edition number of the learning resource.
     *
     * @throws IllegalArgumentException If the provided edition number is negative.
     */
    public LearningResource(int price, int quantity, String title, String description, UUID sellerId, int fidelityPoints, String isbn, String organisation, LocalDate releaseDate, LearningResourceType type, int editionNumber) throws IllegalArgumentException {
        super(price, quantity, title, description, ProductCategory.LearningResource, type, sellerId, fidelityPoints);
        this.isbn = isbn;
        this.organisation = organisation;
        this.releaseDate = releaseDate;
        this.editionNumber = editionNumber;
    }

    /**
     * Returns the ISBN of the learning resource.
     *
     * @return The ISBN of the learning resource.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the learning resource.
     *
     * @param isbn The ISBN to set for the learning resource.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the organization or publisher of the learning resource.
     *
     * @return The organization or publisher of the learning resource.
     */
    public String getOrganisation() {
        return organisation;
    }

    /**
     * Sets the organization or publisher of the learning resource.
     *
     * @param organisation The organization or publisher to set for the learning resource.
     */
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    /**
     * Returns the release date of the learning resource.
     *
     * @return The release date of the learning resource.
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the learning resource.
     *
     * @param releaseDate The release date to set for the learning resource.
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Returns the edition number of the learning resource.
     *
     * @return The edition number of the learning resource.
     */
    public int getEditionNumber() {
        return editionNumber;
    }

    /**
     * Sets the edition number of the learning resource.
     *
     * @param editionNumber The edition number to set for the learning resource.
     *
     * @throws IllegalArgumentException If the provided edition number is negative.
     */
    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }
}
