/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

public class OfficeEquipment extends Product {
    private String brand;
    private String model;
    private OfficeEquipmentCategory subcategory;

    public OfficeEquipment(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, OfficeEquipmentCategory subcategory) {
        super(price, quantity, title, description, ProductCategory.OfficeEquipment, fidelityPoints);
        this.brand = brand;
        this.model = model;
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

    public OfficeEquipmentCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(OfficeEquipmentCategory subcategory) {
        this.subcategory = subcategory;
    }
}
