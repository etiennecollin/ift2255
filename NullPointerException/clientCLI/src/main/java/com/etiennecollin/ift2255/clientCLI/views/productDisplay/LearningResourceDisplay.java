/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.LearningResource;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

/**
 * The {@code LearningResourceDisplay} class is responsible for displaying information about a learning resource product
 * in the CLI application. It extends the {@link ProductDisplay} class and provides specific rendering for learning resource products.
 * <p>
 * The class retrieves the learning resource product information from the {@link ShopController} and displays details such as
 * ISBN, organization, edition number, and release date. The rendering process includes showing the common product information
 * through the superclass and additional details specific to learning resource products.
 */
public class LearningResourceDisplay extends ProductDisplay {
    /**
     * Constructs a new {@code LearningResourceDisplay} with the specified product ID and shop controller.
     *
     * @param productId      The unique identifier of the learning resource product.
     * @param shopController The controller responsible for shop-related actions.
     */
    public LearningResourceDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

    /**
     * Renders the display for the learning resource product, showing both common product information and specific details for learning resources.
     */
    @Override
    public void render() {
        LearningResource lr = shopController.getProduct(LearningResource.class, productId);

        super.renderProductInfo(lr);

        if (lr != null) {
            System.out.println(prettify("ISBN: ") + lr.getIsbn());
            System.out.println(prettify("Organization: ") + lr.getOrganisation());
            System.out.println(prettify("Edition: ") + lr.getEditionNumber());

            System.out.println(prettify("Release date: ") + lr.getReleaseDate());

            waitForKey();
        }
    }
}
