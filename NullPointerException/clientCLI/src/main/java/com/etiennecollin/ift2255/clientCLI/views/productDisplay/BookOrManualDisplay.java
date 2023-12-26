/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.BookOrManual;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class BookOrManualDisplay extends ProductDisplay {

    public BookOrManualDisplay(UUID productId, ShopController shopController) {
        super(productId, shopController);
    }

    @Override
    public void render() {
        BookOrManual book = shopController.getProduct(BookOrManual.class, productId);

        super.renderProductInfo(book);

        if (book != null) {
            System.out.println(prettify("Author: ") + book.getAuthor());
            System.out.println(prettify("Editor: ") + book.getEditor());
            System.out.println(prettify("Edition: ") + book.getEditionNumber());
            System.out.println(prettify("Volume: ") + book.getVolumeNumber());
            System.out.println(prettify("Release date: ") + book.getReleaseDate());

            waitForKey();

            super.renderProductActions(book);
        }
    }
}
