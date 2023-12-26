/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.Review;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The ProductReview class represents a view for submitting a review for a specific product.
 * It allows the user to provide a review by entering content and a rating out of 100.
 */
public class ProductReview extends View {
    /**
     * The ShopController used for managing shop-related functionalities.
     */
    private final ShopController shopController;
    /**
     * The UUID of the product for which the review is being submitted.
     */
    private final UUID productId;

    /**
     * Constructs a ProductReview with the specified product ID and ShopController.
     *
     * @param productId      the UUID of the product for which the review is being submitted.
     * @param shopController the ShopController used for managing shop-related functionalities.
     */
    public ProductReview(UUID productId, ShopController shopController) {
        this.shopController = shopController;
        this.productId = productId;
    }

    /**
     * Renders the ProductReview view, allowing the user to submit a review for the specified product.
     * If the user has already submitted a review for the product, a message is displayed, and no action is taken.
     */
    @Override
    public void render() {
        Review existingReview = shopController.getProductReviewByUser(productId);

        if (existingReview != null) {
            System.out.println(prettify("This product has already been reviewed"));
            waitForKey();
            return;
        }

        //        String title = prettyPrompt("Title of your review", Utils::validateNotEmpty);
        String content = prettyPrompt("Content of your review", Utils::validateNotEmpty);
        int rating = prettyPromptInt("Rating out of 100", number -> Utils.validateNumberRange(number, 0, 100));

        shopController.addReview(productId, content, rating);

        System.out.println(prettify("Review successfully submitted"));
        waitForKey();
    }
}
