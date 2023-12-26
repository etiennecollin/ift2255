/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.OfficeEquipment;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

public class OfficeEquipmentDisplay extends ProductDisplay {
    public OfficeEquipmentDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

    @Override
    public void render() {
        OfficeEquipment oe = shopController.getProduct(OfficeEquipment.class, productId);

        super.renderProductInfo(oe);

        if (oe != null) {
            System.out.println(prettify("Brand: ") + oe.getBrand());
            System.out.println(prettify("Model: ") + oe.getModel());

            waitForKey();
        }
    }
}
