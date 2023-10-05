/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UniShop {
    private final User currentUser;
    private HashMap<Buyer, String> buyerList;
    private HashMap<Seller, String> sellerList;
    private ArrayList<Product> catalog;

    public UniShop(User currentUser, String userListFilePath) {
        this.currentUser = currentUser;
        this.loadUserList(userListFilePath);
        sellerList.forEach((seller, password) -> catalog.addAll(seller.getProductsSold()));
    }

    /**
     * Loads user data from the specified file path.
     *
     * @param path The file path from which user data needs to be loaded.
     */
    private void loadUserList(String path) {
        try (FileInputStream file = new FileInputStream(path)) {
            try (ObjectInputStream input = new ObjectInputStream(file)) {
                this.buyerList = (HashMap<Buyer, String>) input.readObject();
                this.sellerList = (HashMap<Seller, String>) input.readObject();
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

    public HashMap<Buyer, String> getBuyerList() {
        return buyerList;
    }

    public HashMap<Seller, String> getSellerList() {
        return sellerList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addUser(Buyer buyer, String password) {
        if (buyerList.containsKey(buyer)) {
            throw new IllegalArgumentException("This buyer already exists");
        }
        this.buyerList.put(buyer, password);
    }

    public void addUser(Seller seller, String password) {
        if (sellerList.containsKey(seller)) {
            throw new IllegalArgumentException("This seller already exists");
        }
        this.sellerList.put(seller, password);
    }

    public void removeUser(Buyer buyer, String password) {
        if (!buyerList.containsKey(buyer)) {
            throw new IllegalArgumentException("This buyer does not exist");
        }
        this.buyerList.remove(buyer);
    }

    public void removeUser(Seller seller, String password) {
        if (!sellerList.containsKey(seller)) {
            throw new IllegalArgumentException("This seller does not exist");
        }
        this.sellerList.remove(seller);
    }
}
