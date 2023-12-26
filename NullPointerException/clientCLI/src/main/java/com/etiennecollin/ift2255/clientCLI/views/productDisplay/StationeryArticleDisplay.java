/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.StationeryArticle;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

/**
 * The {@code StationeryArticleDisplay} class extends the {@link ProductDisplay} class and is responsible for rendering
 * detailed information about a stationery article product in the CLI application.
 * It provides functionality to display general product information and specific details related to stationery articles,
 * such as brand and model.
 */
public class StationeryArticleDisplay extends ProductDisplay {
    /**
     * Constructs a new {@code StationeryArticleDisplay} with the specified product ID and shop controller.
     *
     * @param productId      The unique identifier of the stationery article product.
     * @param shopController The controller responsible for shop-related actions.
     */
    public StationeryArticleDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

    /**
     * Renders the detailed information about the stationery article product.
     * Displays general product information using the parent class's method and adds specific details such as brand and model.
     * After rendering, it waits for user input before returning.
     */
    @Override
    public void render() {
        StationeryArticle sa = shopController.getProduct(StationeryArticle.class, productId);

        super.renderProductInfo(sa);

        if (sa != null) {
            System.out.println(prettify("Brand: ") + sa.getBrand());
            System.out.println(prettify("Model: ") + sa.getModel());

            waitForKey();
        }
    }
}
