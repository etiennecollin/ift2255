/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.util.UUID;

/**
 * Represents a stationery article product.
 * Extends the abstract class Product and includes additional attributes
 * specific to stationery articles such as brand and model.
 */
public class StationeryArticle extends Product {
    /**
     * The brand of the stationery article.
     */
    private String brand;
    /**
     * The model of the stationery article.
     */
    private String model;

    /**
     * Constructs a StationeryArticle with the specified attributes.
     *
     * @param price          The price of the stationery article.
     * @param quantity       The quantity available for purchase.
     * @param title          The title or name of the stationery article.
     * @param description    A description of the stationery article.
     * @param sellerId       The unique identifier of the seller.
     * @param fidelityPoints The fidelity points associated with the purchase.
     * @param brand          The brand of the stationery article.
     * @param model          The model of the stationery article.
     * @param subCategory    The sub-category of the stationery article.
     *
     * @throws IllegalArgumentException If the price, quantity, or fidelity points are negative.
     */
    public StationeryArticle(int price, int quantity, String title, String description, UUID sellerId, int fidelityPoints, String brand, String model, StationeryArticleCategory subCategory) throws IllegalArgumentException {
        super(price, quantity, title, description, ProductCategory.StationeryArticle, subCategory, sellerId, fidelityPoints);
        this.brand = brand;
        this.model = model;
    }

    /**
     * Gets the brand of the stationery article.
     *
     * @return The brand of the stationery article.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the stationery article.
     *
     * @param brand The brand to set.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the model of the stationery article.
     *
     * @return The model of the stationery article.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the stationery article.
     *
     * @param model The model to set.
     */
    public void setModel(String model) {
        this.model = model;
    }
}
