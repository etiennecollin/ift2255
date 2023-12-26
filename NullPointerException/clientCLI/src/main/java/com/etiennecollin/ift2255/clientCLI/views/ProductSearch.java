/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class ProductSearch extends View {
    private ShopController shopController;

    public ProductSearch(ShopController shopController) {
        this.shopController = shopController;
    }

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
