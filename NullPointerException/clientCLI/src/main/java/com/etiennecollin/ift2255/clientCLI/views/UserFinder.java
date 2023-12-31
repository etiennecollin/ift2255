/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.models.data.Seller;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;
import com.etiennecollin.ift2255.clientCLI.models.data.products.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
     * The ShopController used for interacting with shop-related actions and operations.
     */
    private final ShopController shopController;

    /**
     * Constructs a UserFinder with the specified ProfileController.
     *
     * @param profileController the ProfileController used for interacting with profile-related functionalities.
     */
    public UserFinder(ProfileController profileController, ShopController shopController) {
        this.profileController = profileController;
        this.shopController = shopController;
    }

    /**
     * Renders the UserFinder view, allowing the user to search for buyers and sellers based on various criteria.
     * Overrides the render method in the View class.
     */
    @Override
    public void render() {
        String[] options = {"Go back", "Buyer", "Seller"};
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
        String[] searchBy = {"Go Back", "Name", "Phone number", "Email", "All"};
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
            case 4 -> {
                matchList = profileController.getBuyers();
            }
        }

        profileController.displayBuyers(matchList);
    }

    /**
     * Private method to search for sellers based on specified criteria such as name, address, phone number, and email.
     */
    private void findSeller() {
        clearConsole();
        String[] searchBy = {"Go back", "Name", "Address", "Phone number", "Email", "Offered product category", "All"};
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
            case 5 -> {
                ProductCategory category = prettyMenu("Product category", ProductCategory.class);
                matchList = shopController.getSellersOfCategory(category);
            }
            case 6 -> {
                matchList = profileController.getSellers();
            }
        }

        profileController.displaySellers(matchList);
    }
}
