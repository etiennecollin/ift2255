/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.products.*;

import java.time.LocalDate;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class OfferProduct extends View {
    private ShopController shopController;

    public OfferProduct(ShopController shopController) {
        this.shopController = shopController;
    }

    @Override
    public void render() {
        clearConsole();

        OperationResult result;

        String title = prettyPrompt("Title", Utils::validateNotEmpty);
        ProductCategory productCategory = prettyMenu("Category", ProductCategory.class);
        String description = prettyPrompt("Description");
        int price = prettyPromptCurrency("Price");
        int fidelityPoints = prettyPromptInt("Fidelity points", bonusPoints -> validateBonusFidelityPoints(bonusPoints, price));
        int quantity = prettyPromptInt("Quantity", amount -> validateNumberRange(amount, 0, Integer.MAX_VALUE));

        switch (productCategory) {
            case BookOrManual -> {
                String author = prettyPrompt("Author", Utils::validateNotEmpty);
                String editor = prettyPrompt("Publisher", Utils::validateNotEmpty);
                LocalDate releaseDate = prettyPromptDate("Release date");
                BookOrManualGenre genre = prettyMenu("Genre", BookOrManualGenre.class);
                int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
                int volume = prettyPromptInt("Volume number (enter 0 if not applicable)");
                String isbn = prettyPrompt("ISBN", Utils::validateISBN);

                result = shopController.submitNewBookOrManual(price, quantity, title, description, fidelityPoints, isbn, author, editor, genre, releaseDate, edition, volume);
            }
            case IT -> {
                String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
                String model = prettyPrompt("Model name", Utils::validateNotEmpty);
                LocalDate releaseDate = prettyPromptDate("Release date");
                ITCategory itCategory = prettyMenu("Sub-category", ITCategory.class);

                result = shopController.submitNewITProduct(price, quantity, title, description, fidelityPoints, brand, model, releaseDate, itCategory);
            }
            case LearningResource -> {
                String org = prettyPrompt("Organization", Utils::validateNotEmpty);
                LocalDate releaseDate = prettyPromptDate("Release date");
                LearningResourceType type = prettyMenu("Sub-category", LearningResourceType.class);
                int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
                String isbn = prettyPrompt("ISBN", Utils::validateISBN);

                result = shopController.submitNewLearningResource(price, quantity, title, description, fidelityPoints, isbn, org, releaseDate, type, edition);
            }
            case OfficeEquipment -> {
                String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
                String model = prettyPrompt("Model name", Utils::validateNotEmpty);
                OfficeEquipmentCategory oeCategory = prettyMenu("Sub-category", OfficeEquipmentCategory.class);

                result = shopController.submitNewOfficeEquipment(price, quantity, title, description, fidelityPoints, brand, model, oeCategory);
            }
            case StationeryArticle -> {
                String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
                String model = prettyPrompt("Model name", Utils::validateNotEmpty);
                StationeryArticleCategory saCategory = prettyMenu("Sub-category", StationeryArticleCategory.class);

                result = shopController.submitNewStationeryArticle(price, quantity, title, description, fidelityPoints, brand, model, saCategory);
            }
            default -> result = new OperationResult(false, "Invalid category");
        }

        System.out.println(result.message());
        waitForKey();
    }
}
