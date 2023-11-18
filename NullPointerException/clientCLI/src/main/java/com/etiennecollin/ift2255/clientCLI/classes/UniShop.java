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
            // throw new RuntimeException("Could not load the user list");
        }
        updateCatalog();
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
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Could not create the save file");
        }

        try (FileOutputStream outputFile = new FileOutputStream(file, false)) {
            try (ObjectOutputStream output = new ObjectOutputStream(outputFile)) {
                output.writeObject(getBuyerList());
                output.writeObject(getSellerList());
                output.flush();
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

    public void addUser(Buyer buyer) throws IllegalArgumentException {
        if (buyerList.containsKey(buyer.getUsername().toLowerCase())) {
            throw new IllegalArgumentException("This buyer already exists");
        }
        this.buyerList.put(buyer.getUsername().toLowerCase(), buyer);
    }

    public void addUser(Seller seller) throws IllegalArgumentException {
        if (sellerList.containsKey(seller.getName().toLowerCase())) {
            throw new IllegalArgumentException("This seller already exists");
        }
        this.sellerList.put(seller.getName().toLowerCase(), seller);
        updateCatalog();
    }

    public void removeUser(Buyer buyer) {
        if (!buyerList.containsKey(buyer.getUsername().toLowerCase())) {
            throw new IllegalArgumentException("This buyer does not exist");
        }
        this.buyerList.remove(buyer.getUsername().toLowerCase());
    }

    public void removeUser(Seller seller) {
        if (!sellerList.containsKey(seller.getName().toLowerCase())) {
            throw new IllegalArgumentException("This seller does not exist");
        }
        this.sellerList.remove(seller.getName().toLowerCase());
        updateCatalog();
    }

    public void loginBuyer(String username, String password) throws IllegalArgumentException {
        Buyer buyer = buyerList.get(username.toLowerCase());

        // Check that a buyer is found
        if (buyer == null) {
            throw new IllegalArgumentException("Incorrect username");
        }

        if (buyer.getPassword() != password.hashCode()) {
            throw new IllegalArgumentException("Incorrect password");
        }

        this.setCurrentUser(buyer);
    }

    public void loginSeller(String name, String password) throws IllegalArgumentException {
        Seller seller = sellerList.get(name.toLowerCase());

        if (seller == null) {
            throw new IllegalArgumentException("Incorrect name");
        }

        if (seller.getPassword() != password.hashCode()) {
            throw new IllegalArgumentException("Incorrect password");
        }

        this.setCurrentUser(seller);
    }

    public boolean isPasswordValid(String password) {
        return this.currentUser.getPassword() == password.hashCode();
    }
}
