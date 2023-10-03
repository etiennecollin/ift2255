/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Tuple<Product, Integer>> products;
    /**
     * Total value of cart in cents.
     */
    private int totalPrice;

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void addProduct(Product product) {
        for (Tuple<Product, Integer> tuple : this.products) {
            if (tuple.first == product) {
                totalPrice += tuple.first.getPrice();
                tuple.second++;
                return;
            }
        }
        this.products.add(new Tuple<>(product, 1));
    }

    public void subtractProduct(Product product) throws IllegalArgumentException {
        for (int i = 0; i < this.products.size(); i++) {
            Tuple<Product, Integer> tuple = this.products.get(i);
            if (tuple.first == product) {
                totalPrice -= tuple.first.getPrice();
                if (tuple.second <= 1) {
                    this.products.remove(i);
                } else {
                    tuple.second--;
                }
                break;
            }
        }
        throw new IllegalArgumentException("Product not in cart.");
    }

    public void removeProduct(Product product) throws IllegalArgumentException {
        for (int i = 0; i < this.products.size(); i++) {
            Tuple<Product, Integer> tuple = this.products.get(i);
            if (tuple.first == product) {
                totalPrice -= tuple.first.getPrice() * tuple.second;
                this.products.remove(i);
                break;
            }
        }
        throw new IllegalArgumentException("Product not in cart.");
    }
}
