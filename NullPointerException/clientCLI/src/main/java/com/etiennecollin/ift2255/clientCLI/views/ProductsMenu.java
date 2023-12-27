/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;
import com.etiennecollin.ift2255.clientCLI.models.data.products.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The ProductsMenu class represents a view for displaying and interacting with products.
 * It allows the user to navigate through product categories, select subcategories, and view details of individual products.
 */
public class ProductsMenu extends View {
    /**
     * The ShopController used for managing shop-related functionalities.
     */
    private final ShopController shopController;
    /**
     * The unique identifier of the seller associated with the products.
     */
    private final UUID sellerId;

    /**
     * Constructs a ProductsMenu with the specified seller ID and ShopController.
     *
     * @param sellerId       the unique identifier of the seller associated with the products.
     * @param shopController the ShopController used for managing shop-related functionalities.
     */
    public ProductsMenu(UUID sellerId, ShopController shopController) {
        this.sellerId = sellerId;
        this.shopController = shopController;
    }

    /**
     * Renders the ProductsMenu view, allowing the user to navigate through product categories, select subcategories,
     * and view details of individual products. The user can go back to the main menu or exit the menu at any time.
     */
    @Override
    public void render() {
        while (true) {
            clearConsole();

            ProductCategory selectedCategory = null;
            Enum<?> selectedSubCategory = null;

            // Select category
            ArrayList<String> options = ProductCategory.getOptions();
            options.add("All");
            options.add("Main menu");
            int choice = prettyMenu("Categories", options);
            if (choice == options.size() - 1) break;
            if (choice != options.size() - 2) {
                selectedCategory = ProductCategory.values()[choice];

                // Select subcategory
                ArrayList<String> subOptions = selectedCategory.getSubOptions();
                subOptions.add("All");
                subOptions.add("Main menu");

                int subChoice = prettyMenu("Sub-Categories", subOptions);
                if (subChoice == subOptions.size() - 1) break;
                if (subChoice != subOptions.size() - 2) {
                    selectedSubCategory = selectedCategory.getEnum().getEnumConstants()[subChoice];
                }
            }
            // Get products that match category/subcategory

            List<Product> matchedProducts = shopController.getProducts(selectedCategory, selectedSubCategory, sellerId);

            ArrayList<String> matchedProductsString = new ArrayList<>();
            matchedProductsString.add("Back to categories");
            matchedProductsString.add("Back to main menu");

            for (Product product : matchedProducts) {
                matchedProductsString.add(product.getTitle());
            }

            if (matchedProducts.isEmpty()) {
                System.out.println("------------");
                System.out.println(prettify("No match found"));
                waitForKey();
                continue;
            }

            int answer = prettyMenu("Select a product", matchedProductsString);

            // Check if we go back
            if (answer == 0) {
                continue;
            } else if (answer == 1) {
                break;
            }

            // Get product
            Product product = matchedProducts.get(answer - 2);
            shopController.displayProduct(product);
            break;

            //            if (!prettyPromptBool("Keep browsing product?")) break;
        }
    }
}
