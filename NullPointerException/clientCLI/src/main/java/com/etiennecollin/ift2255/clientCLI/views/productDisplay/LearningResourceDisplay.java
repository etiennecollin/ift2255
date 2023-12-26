/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.LearningResource;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

public class LearningResourceDisplay extends ProductDisplay {
    public LearningResourceDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

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
