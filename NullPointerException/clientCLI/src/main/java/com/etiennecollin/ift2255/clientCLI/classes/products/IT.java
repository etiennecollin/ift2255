/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import com.etiennecollin.ift2255.clientCLI.classes.Seller;

import java.time.LocalDate;

public class IT extends Product {
    private String brand;
    private String model;
    private LocalDate releaseDate;

    public IT(int price, int quantity, String title, String description, Seller seller, int fidelityPoints, String brand, String model, LocalDate releaseDate, ITCategory subCategory) throws IllegalArgumentException {
        super(price, quantity, title, description, ProductCategory.IT, subCategory, seller, fidelityPoints);
        this.brand = brand;
        this.model = model;
        this.releaseDate = releaseDate;
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
}
