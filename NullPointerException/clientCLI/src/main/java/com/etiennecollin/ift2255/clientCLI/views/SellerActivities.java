/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.Order;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code SellerActivities} class represents the view for displaying and managing seller activities in the CLI application.
 * It provides sellers with insights into their recent and overall performance, including revenue, products sold, and product ratings.
 * <p>
 * The class extends the {@link View} class and interacts with the {@link ProfileController} and {@link ShopController} for retrieving and processing data.
 * The seller's activities are computed based on a specified time range, allowing for dynamic insights into recent performance.
 */
public class SellerActivities extends View {
    /**
     * The ProfileController used for interacting with buyer profiles and related actions.
     */
    private final ProfileController profileController;
    /**
     * The ShopController instance for handling shop-related functionalities.
     */
    private final ShopController shopController;

    /**
     * Constructs a new {@code SellerActivities} instance with the specified profile controller and shop controller.
     *
     * @param profileController The controller responsible for seller profile-related actions.
     * @param shopController    The controller responsible for shop-related actions.
     */
    public SellerActivities(ProfileController profileController, ShopController shopController) {
        this.profileController = profileController;
        this.shopController = shopController;
    }

    /**
     * Renders the seller activities view, displaying insights into recent and overall performance.
     * Sellers can view information such as recent revenue, total revenue, recent products sold, total products sold,
     * products offered, recent product rating average, and total product rating average.
     */
    @Override
    public void render() {
        clearConsole();
        int nMonths = prettyPromptInt("Display activities for the last how many months");

        System.out.println(prettify("My activities:"));
        SellerMetrics metrics = getMetrics(nMonths);
        System.out.println(prettify("Recent revenue: " + metrics.recentRevenue()));
        System.out.println(prettify("Total revenue: " + metrics.totalRevenue()));
        System.out.println(prettify("Recent products sold: " + metrics.numberRecentProductsSold()));
        System.out.println(prettify("Total products sold: " + metrics.numberTotalProductsSold()));
        System.out.println(prettify("Products offered: " + metrics.numberProductsOffered()));
        if (metrics.averageRecentProductRating() != -1 && metrics.averageTotalProductRating() != -1) {
            System.out.println(prettify("Recent product rating average: " + metrics.averageRecentProductRating()));
            System.out.println(prettify("Total product rating average: " + metrics.averageTotalProductRating()));
        }
        waitForKey();
    }

    /**
     * Retrieves and computes seller metrics based on the specified time range.
     *
     * @param lastNMonths The number of months for which to retrieve and compute metrics.
     *
     * @return A {@link SellerMetrics} object containing the computed metrics.
     */
    private SellerMetrics getMetrics(int lastNMonths) {
        LocalDate dateCutOff = LocalDate.now().minusMonths(lastNMonths);
        List<Order> ordersSold = shopController.getSellerOrders(profileController.getSeller().getId());
        List<Product> productsOffered = shopController.searchProductsBySeller(profileController.getSeller().getId());

        // Compute recentRevenue and number of products sold.
        int recentRevenue = 0;
        int totalRevenue = 0;
        int numberTotalProductsSold = 0;
        int numberRecentProductsSold = 0;
        for (Order order : ordersSold) {
            if (order.getOrderDate().isAfter(dateCutOff)) {
                recentRevenue += order.getTotalCost();
                numberRecentProductsSold += order.getProducts().size();
            }
            totalRevenue += order.getTotalCost();
            numberTotalProductsSold += order.getProducts().size();
        }

        // Compute average ratings
        int numberRecentRatings = 0;
        int sumRecentRatings = 0;
        int sumTotalRatings = 0;
        int averageRecentRatings = 0;
        int averageTotalRatings = 0;

        try {
            for (Product product : productsOffered) {
                sumTotalRatings += product.getRating();
                if (product.getCommercializationDate().isAfter(dateCutOff)) {
                    sumRecentRatings += product.getRating();
                    numberRecentRatings++;
                }
            }

            if (numberRecentRatings != 0) {
                averageRecentRatings = sumRecentRatings / numberRecentRatings;
            }

            if (!productsOffered.isEmpty()) {
                averageTotalRatings = sumTotalRatings / productsOffered.size();
            }
        } catch (NoSuchElementException e) {
            averageRecentRatings = -1;
            averageTotalRatings = -1;
        }

        return new SellerMetrics(recentRevenue, totalRevenue, numberRecentProductsSold, numberTotalProductsSold, productsOffered.size(), averageRecentRatings, averageTotalRatings);
    }

    /**
     * A record class representing seller metrics, including recent and total revenue, recent and total products sold,
     * the number of products offered, average recent product rating, and average total product rating.
     */
    private record SellerMetrics(int recentRevenue, int totalRevenue, int numberRecentProductsSold,
                                 int numberTotalProductsSold, int numberProductsOffered, int averageRecentProductRating,
                                 int averageTotalProductRating) implements Serializable {}
}
