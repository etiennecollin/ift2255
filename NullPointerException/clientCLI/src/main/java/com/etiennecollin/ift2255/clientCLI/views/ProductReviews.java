/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.models.data.Like;
import com.etiennecollin.ift2255.clientCLI.models.data.Review;

import java.util.ArrayList;
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
        while (true) {
            clearConsole();
            List<Review> reviews = shopController.getProductReviews(this.productId);
            List<Tuple<Review, Buyer>> reviewBuyerList = new ArrayList<>();
            for (Review review : reviews) {
                reviewBuyerList.add(new Tuple<>(review, profileController.getBuyer(review.getAuthorId())));
            }

            if (reviews.isEmpty()) {
                System.out.println(prettify("No reviews for this product"));
                waitForKey();
                return;
            }

            ArrayList<String> reviewStrings = new ArrayList<>();

            //            prettyPaginationMenu(reviewBuyerList, 3, "Select action",
            //                    reviewBuyer -> {
            //                        System.out.println(prettify("--------------------"));
            //                        System.out.println(prettify(reviewBuyer.second.getFirstName() + " " + reviewBuyer.second.getLastName() + " - " + review.getRating() + "/100"));
            //                        System.out.println(prettify(reviewBuyer.first.getComment()));
            //                    }, reviewBuyer -> reviewBuyer.second.getFirstName() + " " + reviewBuyer.second.getLastName(),
            //                    this::reviewActions,
            //                    reviewBuyer -> new Tuple<>(shopController.getReview(reviewBuyer.first.getId())));
            //            for (Review review : reviews) {
            //                Buyer buyerAuthor = profileController.getBuyer(review.getId());
            //                if (buyerAuthor != null) {
            //                    reviewStrings.add(
            //                            prettify("--------------------") + "\n" +
            //                            prettify(buyerAuthor.getFirstName() + " " + buyerAuthor.getLastName() + " - " + review.getRating() + "/100") + "\n" +
            //                            prettify(review.getComment())
            //                    );
            //                }
            //                else {
            //                    Seller sellerAu
            //                }
            //            }

            for (Tuple<Review, Buyer> reviewBuyerTuple : reviewBuyerList) {
                reviewStrings.add(reviewBuyerTuple.second.getFirstName() + " " + reviewBuyerTuple.second.getLastName() + " - " + reviewBuyerTuple.first.getRating() + "/100" + "\n" + prettify(reviewBuyerTuple.first.getComment()) + "\n");
            }

            reviewStrings.add("Go back");
            int index = prettyMenu("Select review", reviewStrings);
            if (index == reviewStrings.size() - 1) break;

            displayActions(reviews.get(index).getId());
        }
    }

    /**
     * Displays the available actions for a specific product review and allows user interaction.
     * This method presents information about the review, including the author, rating, comment, and likes.
     * Users are provided with options such as toggling likes, marking the review as inappropriate, and navigating back.
     * The method handles user input and performs corresponding actions using the {@link ShopController} and {@link ProfileController}.
     *
     * @param reviewId The unique identifier of the product review for which to display actions.
     */
    public void displayActions(UUID reviewId) {
        loop:
        while (true) {
            clearConsole();
            // Get the review and author again in case it was updated.
            Review review = shopController.getProductReview(reviewId);
            Buyer author = profileController.getBuyer(review.getAuthorId());
            List<Like> likes = shopController.getLikes(review.getId());
            boolean isLikedByUser = profileController.isLiked(review.getId());

            System.out.println(prettify(author.getFirstName() + " " + author.getLastName() + " - " + review.getRating() + "/100"));
            System.out.println(prettify(review.getComment()));
            System.out.println(prettify("Likes: " + likes.size()));

            if (isLikedByUser) {
                System.out.println(prettify("You like this review."));
            }
            if (review.getIsReported()) {
                System.out.println(prettify("This review have been marked as inappropriate."));
            }

            if (profileController.getBuyer() != null && author.getId().equals(profileController.getBuyer().getId())) {
                System.out.println(prettify("No actions available on your own review."));
                waitForKey();
                break;
            } else {
                String[] options = {"Go back", "Toggle like", "Mark as inappropriate"};
                int answer = prettyMenu("Select action", options);
                switch (answer) {
                    case 0 -> {
                        break loop;
                    }
                    case 1 -> {
                        OperationResult result = shopController.toggleLikeReview(review.getId());
                        System.out.println(prettify(result.message()));
                        waitForKey();
                    }
                    case 2 -> {
                        OperationResult result = shopController.markReviewAsInappropriate(review.getId());
                        System.out.println(prettify(result.message()));
                        waitForKey();
                    }
                }
            }
        }
    }
}
