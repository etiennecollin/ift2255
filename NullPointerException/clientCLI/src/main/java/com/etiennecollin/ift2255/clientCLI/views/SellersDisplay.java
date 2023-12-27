/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.Seller;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The SellersDisplay class represents a view for displaying information about sellers and interacting with seller-related functionalities.
 * It allows the user to view details about a seller, toggle following the seller, and view the seller's products.
 * <p>
 * The class extends the {@link View} class.
 */
public class SellersDisplay extends View {
    /**
     * The ProfileController instance for interacting with profile-related functionalities.
     */
    private final ProfileController profileController;
    /**
     * The ShopController instance for handling shop-related functionalities.
     */
    private final ShopController shopController;
    /**
     * The list of sellers to be displayed.
     */
    private final List<Seller> sellerList;

    /**
     * Constructs a SellersDisplay with the specified ProfileController, ShopController, and list of sellers.
     *
     * @param profileController the ProfileController used for interacting with profile-related functionalities.
     * @param shopController    the ShopController used for interacting with shop-related functionalities.
     * @param sellerList        the list of sellers to be displayed.
     */
    public SellersDisplay(ProfileController profileController, ShopController shopController, List<Seller> sellerList) {
        this.profileController = profileController;
        this.shopController = shopController;
        this.sellerList = sellerList;
    }

    /**
     * Renders the SellersDisplay view, allowing the user to interact with information about sellers.
     * The user can select a seller, view details, toggle following the seller, and display the seller's products.
     */
    @Override
    public void render() {
        ArrayList<String> matchListString = new ArrayList<>();

        for (Seller seller : sellerList) {
            matchListString.add(seller.getName());
        }

        matchListString.add("Go back");
        while (true) {
            if (sellerList.isEmpty()) {
                System.out.println("------------");
                System.out.println(prettify("No match found"));
                waitForKey();
                break;
            }
            int index = prettyMenu("Select seller", matchListString);
            if (index == matchListString.size() - 1) break;

            Seller seller = sellerList.get(index);
            List<Product> products = shopController.searchProductsBySeller(seller.getId());
            boolean liked = profileController.isLiked(seller.getId());
            int numOrders = shopController.getSellerOrders(seller.getId()).size();

            loop:
            while (true) {
                clearConsole();
                System.out.println(prettify("Name: " + seller.getName()));
                System.out.println(prettify("Email: " + seller.getEmail()));
                System.out.println(prettify("Address: " + seller.getAddress()));
                System.out.println(prettify("Phone number: " + seller.getPhoneNumber()));
                System.out.println(prettify("Number of products offered: " + products.size()));
                System.out.println(prettify("Number of orders sold: " + numOrders));
                System.out.println(prettify(liked ? "You are following this seller." : "You are not following this seller."));

                String[] options = {"Go back", "Toggle follow", "Display seller's products"};
                int answer = prettyMenu("Select action", options);
                switch (answer) {
                    case 0 -> {
                        break loop;
                    }
                    case 1 -> {
                        OperationResult result = profileController.toggleLikeSeller(seller.getId());
                        System.out.println(prettify(result.message()));
                        waitForKey();
                    }
                    case 2 -> shopController.displayProducts(seller.getId());
                }
            }
        }
    }
}
