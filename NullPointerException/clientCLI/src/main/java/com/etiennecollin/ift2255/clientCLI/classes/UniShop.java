/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.classes.products.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UniShop {
    private User currentUser;
    private HashMap<String, Buyer> buyerList;
    private HashMap<String, Seller> sellerList;
    private ArrayList<Product> catalog;

    public UniShop() {
        this.buyerList = new HashMap<>();
        this.sellerList = new HashMap<>();
        updateCatalog();
    }

    public void updateCatalog() {
        this.catalog = new ArrayList<>();
        sellerList.forEach((sellerName, seller) -> this.catalog.addAll(seller.getProductsOffered()));
    }

    /**
     * Loads user data from the specified file path.
     *
     * @param path The file path from which user data needs to be loaded.
     */
    @SuppressWarnings("unchecked")
    public void loadUserList(String path) {
        try (FileInputStream file = new FileInputStream(path)) {
            try (ObjectInputStream input = new ObjectInputStream(file)) {
                this.buyerList = (HashMap<String, Buyer>) input.readObject();
                this.sellerList = (HashMap<String, Seller>) input.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Could not load the user list");
        }
    }

    public ArrayList<Product> getCatalog() {
        return catalog;
    }

    /**
     * Saves the current state of buyer and seller lists to the specified file path.
     *
     * @param path The file path to which user data needs to be saved.
     */
    public void saveUserList(String path) {
        try (FileOutputStream file = new FileOutputStream(path)) {
            try (ObjectOutputStream output = new ObjectOutputStream(file)) {
                output.writeObject(getBuyerList());
                output.writeObject(getSellerList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save the user list");
        }
    }

    public HashMap<String, Buyer> getBuyerList() {
        return buyerList;
    }

    public HashMap<String, Seller> getSellerList() {
        return sellerList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void addUser(Buyer buyer) {
        if (buyerList.containsKey(buyer.getUsername())) {
            throw new IllegalArgumentException("This buyer already exists");
        }
        this.buyerList.put(buyer.getUsername(), buyer);
    }

    public void addUser(Seller seller) {
        if (sellerList.containsKey(seller.getName())) {
            throw new IllegalArgumentException("This seller already exists");
        }
        this.sellerList.put(seller.getName(), seller);
        updateCatalog();
    }

    public void removeUser(Buyer buyer) {
        if (!buyerList.containsKey(buyer.getUsername())) {
            throw new IllegalArgumentException("This buyer does not exist");
        }
        this.buyerList.remove(buyer.getUsername());
    }

    public void removeUser(Seller seller) {
        if (!sellerList.containsKey(seller.getName())) {
            throw new IllegalArgumentException("This seller does not exist");
        }
        this.sellerList.remove(seller.getName());
        updateCatalog();
    }

    public void loginBuyer(String username, String password) {
        Buyer buyer = buyerList.get(username);

        // Check that a buyer is found
        if (buyer == null) {
            throw new IllegalArgumentException("Incorrect username");
        }

        if (buyer.getPassword() != password.hashCode()) {
            throw new IllegalArgumentException("Incorrect password");
        }

        this.setCurrentUser(buyer);
    }

    public void loginSeller(String name, String password) {
        Seller seller = sellerList.get(name);

        if (seller == null) {
            throw new IllegalArgumentException("Incorrect name");
        }

        if (seller.getPassword() != password.hashCode()) {
            throw new IllegalArgumentException("Incorrect password");
        }

        this.setCurrentUser(seller);
    }
}
