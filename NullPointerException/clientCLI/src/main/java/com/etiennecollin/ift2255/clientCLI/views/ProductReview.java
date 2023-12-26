/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.Review;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class ProductReview extends View {
    private final ShopController shopController;
    private final UUID productId;

    public ProductReview(UUID productId, ShopController shopController) {
        this.shopController = shopController;
        this.productId = productId;
    }

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
