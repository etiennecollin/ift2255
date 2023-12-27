/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.models.data.Review;

import java.util.List;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The ProductReviews class represents a view for displaying reviews of a specific product.
 * It allows the user to view reviews in batches of 3, showing the author's name, rating, and comment.
 */
public class ProductReviews extends View {
    /**
     * The ShopController used for managing shop-related functionalities.
     */
    private final ShopController shopController;
    /**
     * The ProfileController used for interacting with profile-related functionalities.
     */
    private final ProfileController profileController;
    /**
     * The UUID of the product for which reviews are being displayed.
     */
    private final UUID productId;

    /**
     * Constructs a ProductReviews with the specified product ID, ShopController, and ProfileController.
     *
     * @param productId         the UUID of the product for which reviews are being displayed.
     * @param shopController    the ShopController used for managing shop-related functionalities.
     * @param profileController the ProfileController used for interacting with profile-related functionalities.
     */
    public ProductReviews(UUID productId, ShopController shopController, ProfileController profileController) {
        this.productId = productId;
        this.shopController = shopController;
        this.profileController = profileController;
    }

    /**
     * Renders the ProductReviews view, displaying reviews of the specified product in batches of 3.
     * The user can choose to see more reviews or go back to the previous menu.
     */
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
