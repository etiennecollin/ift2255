/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The ProductSearch class represents a view for searching and displaying products based on keyword matches.
 * It allows the user to enter a keyword, search for products with matching titles or descriptions, and view details
 * of individual products from the search results.
 */
public class ProductSearch extends View {
    /**
     * The ShopController used for managing shop-related functionalities.
     */
    private final ShopController shopController;

    /**
     * Constructs a ProductSearch with the specified ShopController.
     *
     * @param shopController the ShopController used for managing shop-related functionalities.
     */
    public ProductSearch(ShopController shopController) {
        this.shopController = shopController;
    }

    /**
     * Renders the ProductSearch view, allowing the user to enter a keyword, search for products with matching titles
     * or descriptions, and view details of individual products from the search results. The user can go back to the
     * main menu or exit the search at any time.
     */
    @Override
    public void render() {
        while (true) {
            clearConsole();

            ArrayList<String> searchResultsString = new ArrayList<>();
            searchResultsString.add("Main Menu");

            String keyWord = prettyPrompt("Search").toLowerCase();

            List<Product> searchResults = shopController.searchProductsTitleDescription(keyWord);

            for (Product p : searchResults) {
                searchResultsString.add(p.getTitle());
            }

            if (searchResults.isEmpty()) {
                System.out.println(prettify("No match found"));
                if (!prettyPromptBool("Keep browsing product?")) break;
                continue;
            }

            int answer = prettyMenu("Select a product", searchResultsString);

            if (answer == 0) {
                break;
            } else {
                // Get product
                Product product = searchResults.get(answer - 1); // adjust to product array
                shopController.displayProduct(product);
                break;
            }

            //            if (!prettyPromptBool("Keep browsing product?")) break;
        }
    }
}
