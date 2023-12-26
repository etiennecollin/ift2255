/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.Seller;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;
import com.etiennecollin.ift2255.clientCLI.views.View;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code ProductDisplay} class is an abstract class responsible for displaying information about a product in the CLI application.
 * It serves as the base class for specific product display implementations and provides common functionality for rendering product details and actions.
 * <p>
 * The class extends the {@link View} class.
 * <p>
 * The class includes methods for rendering product information and actions such as toggling likes, displaying reviews, and adding products to the cart.
 * The rendering is done through interaction with the {@link ShopController} and related data classes.
 */
public abstract class ProductDisplay extends View {
    /**
     * The unique identifier of the product to be displayed.
     */
    protected UUID productId;
    /**
     * The controller responsible for shop-related actions.
     */
    protected ShopController shopController;

    /**
     * Constructs a new {@code ProductDisplay} with the specified product ID and shop controller.
     *
     * @param productId      The unique identifier of the product.
     * @param shopController The controller responsible for shop-related actions.
     */
    public ProductDisplay(UUID productId, ShopController shopController) {
        this.productId = productId;
        this.shopController = shopController;
    }

    /**
     * Renders the common product information shared by all products.
     * Displays details such as title, likes, rating, description, category, sub-category, price, bonus fidelity points,
     * promotional discount, promotional bonus fidelity points, promotional end date, seller, and commercialization date.
     *
     * @param product The product for which to render information.
     */
    public void renderProductInfo(Product product) {
        if (product == null) {
            System.out.println(prettify("This product no longer exists."));
            return;
        }

        clearConsole();
        System.out.println(prettify("Title: " + product.getTitle()));
        System.out.println(prettify("Likes: ") + product.getLikes());
        System.out.println(prettify("Rating: ") + product.getRating());
        System.out.println(prettify("Description: ") + product.getDescription());
        System.out.println(prettify("Category: ") + product.getCategory());
        System.out.println(prettify("Sub-category: ") + product.getSubCategory());
        System.out.println(prettify("Price: ") + Utils.formatMoney(product.getPrice()));
        System.out.println(prettify("Fidelity Points: ") + product.getBonusFidelityPoints());

        if (product.getPromoDiscount() != 0) {
            System.out.println(prettify("Promotional discount: ") + Utils.formatMoney(product.getPromoDiscount()));
        }
        if (product.getPromoFidelityPoints() != 0) {
            System.out.println(prettify("Promotional bonus fidelity points: ") + product.getPromoFidelityPoints());
        }
        if (product.getPromoEndDate() != null) {
            System.out.println(prettify("Promotional ends on: ") + product.getPromoEndDate());
        }

        Seller seller = shopController.getSeller(product.getSellerId());
        System.out.println(prettify("Sold by: ") + seller.getName());

        System.out.println(prettify("Commercialization date: ") + product.getCommercializationDate());
    }

    /**
     * Renders the product-specific actions menu, allowing the user to perform actions such as toggling likes, displaying reviews, and adding to the cart.
     *
     * @param product The product for which to render actions.
     */
    public void renderProductActions(Product product) {
        String[] options = {"Go back", "Toggle like", "Display reviews", "Add to cart"};
        while (true) {
            clearConsole();
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    OperationResult result = shopController.toggleLike(product.getId());
                    System.out.println(prettify(result.message()));
                }
                case 2 -> {
                    shopController.displayReviews(product.getId());
                }
                case 3 -> {
                    int qty = prettyPromptInt("Quantity", (quantity) -> shopController.validateQuantity(product.getId(), quantity));

                    OperationResult result = shopController.addToCart(product.getId(), qty);

                    System.out.println(prettify(result.message()));
                }
            }

            if (!prettyPromptBool("New action?")) break;
        }
    }
}
