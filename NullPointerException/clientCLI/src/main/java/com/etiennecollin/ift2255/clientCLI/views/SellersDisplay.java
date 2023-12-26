/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.Seller;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class SellersDisplay extends View {
    private final ProfileController profileController;
    private final ShopController shopController;
    private final List<Seller> sellerList;

    public SellersDisplay(ProfileController profileController, ShopController shopController, List<Seller> sellerList) {
        this.profileController = profileController;
        this.shopController = shopController;
        this.sellerList = sellerList;
    }

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
