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

public abstract class ProductDisplay extends View {
    protected UUID productId;
    protected ShopController shopController;

    public ProductDisplay(UUID productId, ShopController shopController) {
        this.productId = productId;
        this.shopController = shopController;
    }

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
