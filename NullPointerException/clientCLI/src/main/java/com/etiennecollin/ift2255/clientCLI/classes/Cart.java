/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Cart implements Serializable {
    private final Buyer buyer;
    private ArrayList<Tuple<Product, Integer>> products;
    /**
     * Total value of cart in cents.
     */
    private int cost;
    private int numberOfProducts;
    private int numberOfFidelityPoints;

    public Cart(Buyer buyer) {
        this.cost = 0;
        this.numberOfFidelityPoints = 0;
        this.numberOfProducts = 0;
        this.products = new ArrayList<Tuple<Product, Integer>>();
        this.buyer = buyer;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void addProduct(Product product, int quantity) {
        // Check if product already is in cart
        boolean isAlreadyInCart = false;
        for (Tuple<Product, Integer> tuple : this.products) {
            if (tuple.first == product) {
                tuple.second += quantity;
                isAlreadyInCart = true;
                break;
            }
        }

        numberOfProducts += quantity;
        cost += product.getCost() * quantity;
        numberOfFidelityPoints += (product.getBonusFidelityPoints() + product.getCost() / 100) * quantity;

        if (!isAlreadyInCart) {
            this.products.add(new Tuple<>(product, quantity));
        }
    }

    public void subtractProduct(Product product, int quantity) throws IllegalArgumentException {
        for (int i = 0; i < this.products.size(); i++) {
            Tuple<Product, Integer> tuple = this.products.get(i);
            // Check if product is in cart
            if (tuple.first == product) {
                if (quantity > tuple.second) {
                    throw new IllegalArgumentException("Cannot remove more products than are present in the cart");
                }

                numberOfProducts -= quantity;
                cost -= product.getCost() * quantity;
                numberOfFidelityPoints -= (product.getBonusFidelityPoints() + product.getCost() / 100) * quantity;

                if (tuple.second == quantity) {
                    this.products.remove(i);
                } else {
                    tuple.second -= quantity;
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
                numberOfProducts -= tuple.second;
                cost -= product.getCost() * tuple.second;
                numberOfFidelityPoints -= product.getBonusFidelityPoints() * tuple.second;
                this.products.remove(i);
                break;
            }
        }
        throw new IllegalArgumentException("Product not in cart.");
    }

    public void createOrder(String email, int phone, String address, String billingAddress, String creditCardName, int creditCardNumber, int creditCardExp, int creditCardSecretDigits) {
        // "Sort" products by seller
        HashMap<Seller, ArrayList<Tuple<Product, Integer>>> hashmap = new HashMap<>();
        for (Tuple<Product, Integer> tuple : this.products) {
            ArrayList<Tuple<Product, Integer>> newValue = hashmap.getOrDefault(tuple.first.getSeller(), new ArrayList<>());
            newValue.add(tuple);
            hashmap.put(tuple.first.getSeller(), newValue);

            // Update product quantity
            if (tuple.second > tuple.first.getQuantity()) {
                throw new RuntimeException("Cannot buy more of a product than is available");
            }
            tuple.first.setQuantity(tuple.first.getQuantity() - tuple.second);
        }

        // For each seller and tuples of Product/Quantity in the hashmap
        hashmap.forEach((seller, tuples) -> {
            int subTotalCost = 0;
            int subTotalFidelityPoints = 0;

            // Compute the cost and fidelity points of this sub-order
            for (Tuple<Product, Integer> tuple : tuples) {
                Product product = tuple.first;
                int quantity = tuple.second;
                subTotalCost += product.getCost() * quantity;
                subTotalFidelityPoints += (product.getCost() / 100 + product.getBonusFidelityPoints()) * quantity;
            }

            // Create the sub-order
            Order order = new Order(subTotalCost, subTotalFidelityPoints, tuples, email, phone, address, billingAddress, creditCardName, creditCardNumber, creditCardExp, creditCardSecretDigits, this.buyer, seller);
            this.buyer.addOrder(order);
            seller.addOrderSold(order);

            // Send notification
            String content = "Order " + order.getId() + " by " + order.getBuyer().getFirstName() + " " + order.getBuyer().getLastName() + " was received on " + order.getOrderDate() + ".";
            Notification notification = new Notification("Order received", content);
            seller.addNotification(notification);
        });

        emptyCart();
    }

    public void emptyCart() {
        this.products.clear();
        this.numberOfFidelityPoints = 0;
        this.cost = 0;
    }

    public int getCost() {
        return cost;
    }

    public int getNumberOfFidelityPoints() {
        return numberOfFidelityPoints;
    }

    public ArrayList<Tuple<Product, Integer>> getProducts() {
        return products;
    }
}
