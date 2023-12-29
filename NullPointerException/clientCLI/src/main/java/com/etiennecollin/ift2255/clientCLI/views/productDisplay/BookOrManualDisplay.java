/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.products.BookOrManual;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
import static com.etiennecollin.ift2255.clientCLI.Utils.waitForKey;

/**
 * The {@code BookOrManualDisplay} class represents a view for displaying detailed information about a book or a manual
 * in the CLI application. It extends the {@link ProductDisplay} class and is specifically designed to handle
 * instances of the {@link BookOrManual} class.
 * <p>
 * The class provides methods to render information about a book or manual, including its author, editor, edition number,
 * volume number, and release date. Additionally, it inherits methods from the {@link ProductDisplay} class for rendering
 * general product information and actions.
 */
public class BookOrManualDisplay extends ProductDisplay {
    /**
     * Constructs a new instance of {@code BookOrManualDisplay}.
     *
     * @param productId      The unique identifier of the Book or Manual product to display.
     * @param shopController The controller managing interactions related to the shop.
     */
    public BookOrManualDisplay(UUID productId, ShopController shopController, ProfileController profileController) {
        super(productId, shopController, profileController);
    }

    /**
     * Renders the detailed information and actions for the displayed Book or Manual product.
     * Overrides the {@link ProductDisplay#render()} method.
     */
    @Override
    public void render() {
        while (true) {
            BookOrManual book = shopController.getProduct(BookOrManual.class, productId);

            super.renderProductInfo(book);

            if (book != null) {
                System.out.println(prettify("Author: ") + book.getAuthor());
                System.out.println(prettify("Editor: ") + book.getEditor());
                System.out.println(prettify("Edition: ") + book.getEditionNumber());
                System.out.println(prettify("Volume: ") + book.getVolumeNumber());
                System.out.println(prettify("Release date: ") + book.getReleaseDate());

                waitForKey();

                boolean repeat = super.renderProductActions(book);
                if (!repeat) {
                    return;
                }
            }
        }
    }
}
