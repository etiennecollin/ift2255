/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.products.OfficeEquipment;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

/**
 * The {@code OfficeEquipmentDisplay} class is responsible for displaying information about an office equipment product
 * in the CLI application. It extends the {@link ProductDisplay} class and provides specific rendering for office equipment products.
 * <p>
 * The class retrieves the office equipment product information from the {@link ShopController} and displays details such as
 * brand, models, and common product information through the superclass. It includes specific rendering for office equipment details.
 */
public class OfficeEquipmentDisplay extends ProductDisplay {
    /**
     * Constructs a new {@code OfficeEquipmentDisplay} with the specified product ID and shop controller.
     *
     * @param productId      The unique identifier of the office equipment product.
     * @param shopController The controller responsible for shop-related actions.
     */
    public OfficeEquipmentDisplay(UUID productId, ShopController shopController, ProfileController profileController) {
        super(productId, shopController, profileController);
    }

    /**
     * Renders the display for the office equipment product, showing both common product information and specific details for office equipment.
     */
    @Override
    public void render() {
        while (true) {
            OfficeEquipment oe = shopController.getProduct(OfficeEquipment.class, productId);

            super.renderProductInfo(oe);

            if (oe != null) {
                System.out.println(prettify("Brand: ") + oe.getBrand());
                System.out.println(prettify("Model: ") + oe.getModel());

                waitForKey();

                boolean repeat = super.renderProductActions(oe);
                if (!repeat) {
                    return;
                }
            }
        }
    }
}
