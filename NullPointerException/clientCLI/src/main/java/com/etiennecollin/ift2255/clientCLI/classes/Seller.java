/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Seller extends User {
    private final ArrayList<Product> productsSold;
    private String name; // Unique

    public Seller(String name, String email, int phone, String address) {
        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
        this.setAddress(address);
        this.productsSold = new ArrayList<>();
    }

    public ArrayList<Product> getProductsSold() {
        return productsSold;
    }

    public void removeProductSold(Product product) throws IllegalArgumentException {
        if (!productsSold.contains(product)) {
            throw new IllegalArgumentException("This product is not sold by this vendor");
        }
        productsSold.remove(product);
    }

    public void addProductSold(Product product) throws IllegalArgumentException {
        if (productsSold.contains(product)) {
            throw new IllegalArgumentException("This product is already sold by this vendor");
        }
        productsSold.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(getName(), seller.getName()) && Objects.equals(getId(), seller.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
