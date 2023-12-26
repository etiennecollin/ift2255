/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.model.data.Review;

import java.util.List;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class ProductReviews extends View {
    private final ShopController shopController;
    private final ProfileController profileController;
    private final UUID productId;

    public ProductReviews(UUID productId, ShopController shopController, ProfileController profileController) {
        this.productId = productId;
        this.shopController = shopController;
        this.profileController = profileController;
    }

    @Override
    public void render() {
        clearConsole();
        List<Review> reviews = shopController.getProductReviews(this.productId);

        if (reviews.isEmpty()) {
            System.out.println(prettify("No reviews for this product"));
            waitForKey();
            return;
        }

        // Print reviews in batches of 3
        int itemsPerPage = 3;
        for (int i = 0; i < reviews.size(); i += itemsPerPage) {
            clearConsole();
            int itemsOnPage = Math.min(itemsPerPage, reviews.size() - i);

            System.out.println(prettify("Reviews " + (i + 1) + " to " + (i + itemsOnPage) + ":"));
            for (int j = i; j < i + itemsOnPage; j++) {
                Review review = reviews.get(j);
                Buyer author = profileController.getBuyer(review.getAuthorId());
                System.out.println(prettify("--------------------"));
                System.out.println(prettify(author.getFirstName() + " " + author.getLastName() + " - " + review.getRating() + "/100"));
//                System.out.println(prettify("Title: " + review.getTitle()));
                System.out.println(prettify(review.getComment()));
            }

            if (!prettyPromptBool("See more reviews?")) break;
        }
    }
}
