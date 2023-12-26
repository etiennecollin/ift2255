/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.IT;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

/**
 * The {@code ITProductDisplay} class is responsible for displaying information about an IT product in the CLI application.
 * It extends the {@link ProductDisplay} class and provides specific rendering for IT products.
 * <p>
 * The class retrieves the IT product information from the {@link ShopController} and displays details such as brand, model,
 * and release date. The rendering process includes showing the common product information through the superclass and
 * additional details specific to IT products.
 */
public class ITProductDisplay extends ProductDisplay {
    /**
     * Constructs a new {@code ITProductDisplay} with the specified product ID and shop controller.
     *
     * @param productId      The unique identifier of the IT product.
     * @param shopController The controller responsible for shop-related actions.
     */
    public ITProductDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

    /**
     * Renders the display for the IT product, showing both common product information and specific details for IT products.
     */
    @Override
    public void render() {
        IT it = shopController.getProduct(IT.class, productId);

        super.renderProductInfo(it);

        if (it != null) {
            System.out.println(prettify("Brand: ") + it.getBrand());
            System.out.println(prettify("Model: ") + it.getModel());

            System.out.println(prettify("Release date: ") + it.getReleaseDate());

            waitForKey();
        }
    }
}
