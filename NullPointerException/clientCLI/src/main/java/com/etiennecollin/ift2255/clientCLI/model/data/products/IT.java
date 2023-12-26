/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents an Information Technology (IT) product.
 * <p>
 * This class extends the {@link Product} class and includes additional properties such as brand, model, and release date.
 */
public class IT extends Product {
    /**
     * The brand of the IT product.
     */
    private String brand;
    /**
     * The model of the IT product.
     */
    private String model;
    /**
     * The release date of the IT product.
     */
    private LocalDate releaseDate;

    /**
     * Constructs an IT product with specified properties.
     *
     * @param price          The price of the IT product.
     * @param quantity       The quantity of the IT product available in stock.
     * @param title          The title or name of the IT product.
     * @param description    The description of the IT product.
     * @param sellerId       The unique identifier of the seller offering the IT product.
     * @param fidelityPoints The fidelity points associated with the purchase of the IT product.
     * @param brand          The brand of the IT product.
     * @param model          The model of the IT product.
     * @param releaseDate    The release date of the IT product.
     * @param subCategory    The specific category within the broader IT category.
     *
     * @throws IllegalArgumentException If an invalid argument is provided.
     */
    public IT(int price, int quantity, String title, String description, UUID sellerId, int fidelityPoints, String brand, String model, LocalDate releaseDate, ITCategory subCategory) throws IllegalArgumentException {
        super(price, quantity, title, description, ProductCategory.IT, subCategory, sellerId, fidelityPoints);
        this.brand = brand;
        this.model = model;
        this.releaseDate = releaseDate;
    }

    /**
     * Returns the brand of the IT product.
     *
     * @return The brand of the IT product.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the IT product.
     *
     * @param brand The brand of the IT product.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns the model of the IT product.
     *
     * @return The model of the IT product.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the IT product.
     *
     * @param model The model of the IT product.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the release date of the IT product.
     *
     * @return The release date of the IT product.
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the IT product.
     *
     * @param releaseDate The release date of the IT product.
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
