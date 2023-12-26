/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.IT;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

public class ITProductDisplay extends ProductDisplay {
    public ITProductDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

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
