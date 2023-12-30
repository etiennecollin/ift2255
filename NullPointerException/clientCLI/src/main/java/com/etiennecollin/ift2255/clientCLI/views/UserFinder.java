/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.models.data.Seller;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The UserFinder class represents a view for searching and displaying information about buyers and sellers.
 * It allows the user to search for buyers and sellers based on different criteria such as name, phone number, and email.
 * <p>
 * The class extends the {@link View} class.
 */
public class UserFinder extends View {
    /**
     * The ProfileController used for interacting with profile-related functionalities.
     */
    private final ProfileController profileController;

    /**
     * Constructs a UserFinder with the specified ProfileController.
     *
     * @param profileController the ProfileController used for interacting with profile-related functionalities.
     */
    public UserFinder(ProfileController profileController) {
        this.profileController = profileController;
    }

    /**
     * Renders the UserFinder view, allowing the user to search for buyers and sellers based on various criteria.
     * Overrides the render method in the View class.
     */
    @Override
    public void render() {
        String[] options = {"Main menu", "Buyer", "Seller"};
        clearConsole();
        int answer = prettyMenu("Search for", options);
        switch (answer) {
            case 0 -> {
            }
            case 1 -> findBuyer();
            case 2 -> findSeller();
        }
    }

    /**
     * Private method to search for buyers based on specified criteria such as name, phone number, and email.
     */
    private void findBuyer() {

        clearConsole();
        String[] searchBy = {"Go Back", "Name", "Phone number", "email"};
        int search = prettyMenu("Search buyer by", searchBy);

        List<Buyer> matchList = new ArrayList<>();
        switch (search) {
            case 0 -> {
                return;
            }
            case 1 -> {
                String name = prettyPrompt("Name").toLowerCase();
                matchList = profileController.searchBuyerName(name);
            }
            case 2 -> {
                String phoneNumber = prettyPrompt("Phone number");
                matchList = profileController.searchBuyerPhone(phoneNumber);
            }
            case 3 -> {
                String email = prettyPrompt("Email").toLowerCase();
                matchList = profileController.searchBuyerEmail(email);
            }
        }

        profileController.displayBuyers(matchList);
    }

    /**
     * Private method to search for sellers based on specified criteria such as name, address, phone number, and email.
     */
    private void findSeller() {
        clearConsole();
        String[] searchBy = {"Go back", "Name", "Address", "Phone number", "email"};
        int search = prettyMenu("Search seller by", searchBy);

        List<Seller> matchList = new ArrayList<>();
        switch (search) {
            case 0 -> {
                return;
            }
            case 1 -> {
                String name = prettyPrompt("Name").toLowerCase();
                matchList = profileController.searchSellerName(name);
            }
            case 2 -> {
                String address = prettyPrompt("Address").toLowerCase();
                matchList = profileController.searchSellerAddress(address);
            }
            case 3 -> {
                String phoneNumber = prettyPrompt("Phone number");
                matchList = profileController.searchSellerPhone(phoneNumber);
            }
            case 4 -> {
                String email = prettyPrompt("Email").toLowerCase();
                matchList = profileController.searchSellerEmail(email);
            }
        }

        profileController.displaySellers(matchList);
    }
}
