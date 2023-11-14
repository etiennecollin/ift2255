/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import java.time.LocalDate;

public class IT extends Product {
    private String brand;
    private String model;
    private LocalDate releaseDate;
    private ITCategory subcategory;

    public IT(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, LocalDate releaseDate, ITCategory subcategory) {
        super(price, quantity, title, description, ProductCategory.IT, fidelityPoints);
        this.brand = brand;
        this.model = model;
        this.releaseDate = releaseDate;
        this.subcategory = subcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ITCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(ITCategory subcategory) {
        this.subcategory = subcategory;
    }
}
