/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.StationeryArticle;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

public class StationeryArticleDisplay extends ProductDisplay {
    public StationeryArticleDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

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
