/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.Like;
import com.etiennecollin.ift2255.clientCLI.models.data.Order;
import com.etiennecollin.ift2255.clientCLI.models.data.Review;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code BuyerActivities} class represents the view for displaying and managing buyer activities in the CLI application.
 * It provides buyers with insights into their recent and overall performance, including orders, products bought, likes, and reviews.
 * <p>
 * The class extends the {@link View} class and interacts with the {@link ProfileController} and {@link ShopController} for retrieving and processing data.
 * Buyer activities are computed based on a specified time range, allowing for dynamic insights into recent performance.
 */
public class BuyerActivities extends View {
    /**
     * The ProfileController used for interacting with buyer profiles and related actions.
     */
    private final ProfileController profileController;
    /**
     * The ShopController instance for handling shop-related functionalities.
     */
    private final ShopController shopController;

    /**
     * Constructs a new {@code BuyerActivities} instance with the specified profile controller and shop controller.
     *
     * @param profileController The controller responsible for buyer profile-related actions.
     * @param shopController    The controller responsible for shop-related actions.
     */
    public BuyerActivities(ProfileController profileController, ShopController shopController) {
        this.profileController = profileController;
        this.shopController = shopController;
    }

    /**
     * Renders the buyer activities view, displaying insights into recent and overall performance.
     * Buyers can view information such as recent orders, total orders, recent products bought, total products bought,
     * products liked, average recent reviews, average total reviews, recent reviews, and total reviews.
     */
    @Override
    public void render() {
        clearConsole();
        int nMonths = prettyPromptInt("Display activities for the last how many months");

        System.out.println(prettify("My activities:"));
        BuyerMetrics metrics = getMetrics(nMonths);
        System.out.println(prettify("Recent orders: " + metrics.numberRecentOrders()));
        System.out.println(prettify("Total orders: " + metrics.numberTotalOrders()));
        System.out.println(prettify("Recent product bought: " + metrics.numberRecentProductsBought()));
        System.out.println(prettify("Total product bought: " + metrics.numberTotalProductsBought()));
        System.out.println(prettify("Number of products liked: " + metrics.numberProductsLiked()));
        System.out.println(prettify("Average recent reviews: " + metrics.averageRecentReviews()));
        System.out.println(prettify("Average total reviews: " + metrics.averageTotalReviews()));
        System.out.println(prettify("Recent reviews: " + metrics.averageRecentReviews()));
        System.out.println(prettify("Total reviews: " + metrics.numberTotalReviews()));
        waitForKey();
    }

    /**
     * Retrieves and computes buyer metrics based on the specified time range.
     *
     * @param lastNMonths The number of months for which to retrieve and compute metrics.
     *
     * @return A {@link BuyerMetrics} object containing the computed metrics.
     */
    private BuyerMetrics getMetrics(int lastNMonths) {
        LocalDate dateCutOff = LocalDate.now().minusMonths(lastNMonths);
        UUID buyerId = profileController.getBuyer().getId();
        List<Order> orders = shopController.getBuyerOrders();
        List<Review> reviewsWritten = profileController.getReviewsByAuthor(buyerId);
        List<Like> productLikesByBuyer = profileController.getProductLikesByBuyer(buyerId);

        // Compute recentRevenue and number of products sold.
        int numberRecentProductsBought = 0;
        int numberTotalProductsBought = 0;
        int numberRecentOrders = 0;
        int numberTotalOrders = 0;
        for (Order order : orders) {
            if (order.getOrderDate().isAfter(dateCutOff)) {
                numberRecentProductsBought += order.getProducts().size();
                numberRecentOrders++;
            }
            numberTotalProductsBought += order.getProducts().size();
            numberTotalOrders++;
        }

        int numberRecentReviews = 0;
        int sumRecentReviews = 0;
        int sumTotalReviews = 0;
        int averageRecentReviews = 0;
        int averageTotalReviews = 0;

        for (Review review : reviewsWritten) {
            sumTotalReviews += review.getRating();
            if (review.getCreationDate().isAfter(dateCutOff)) {
                sumRecentReviews += review.getRating();
                numberRecentReviews++;
            }
        }

        if (numberRecentReviews != 0) {
            averageRecentReviews = sumRecentReviews / numberRecentReviews;
        }

        if (!reviewsWritten.isEmpty()) {
            averageTotalReviews = sumTotalReviews / reviewsWritten.size();
        }

        return new BuyerMetrics(numberRecentOrders, numberTotalOrders, numberRecentProductsBought, numberTotalProductsBought, productLikesByBuyer.size(), averageRecentReviews, averageTotalReviews, numberRecentReviews, reviewsWritten.size());
    }

    /**
     * A record class representing buyer metrics, including recent and total orders, recent and total products bought,
     * the number of products liked, average recent reviews, average total reviews, recent reviews, and total reviews.
     */
    public record BuyerMetrics(int numberRecentOrders, int numberTotalOrders, int numberRecentProductsBought,
                               int numberTotalProductsBought, int numberProductsLiked, int averageRecentReviews,
                               int averageTotalReviews, int numberRecentReviews,
                               int numberTotalReviews) implements Serializable {}
}
