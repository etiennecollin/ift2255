/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views.productDisplay;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.models.data.Seller;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;
import com.etiennecollin.ift2255.clientCLI.views.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code ProductDisplay} class is an abstract class responsible for displaying information about a product in the CLI application.
 * It serves as the base class for specific product display implementations and provides common functionality for rendering product details and actions.
 * <p>
 * The class extends the {@link View} class.
 * <p>
 * The class includes methods for rendering product information and actions such as toggling likes, displaying reviews, and adding products to the cart.
 * The rendering is done through interaction with the {@link ShopController} and related data classes.
 */
public abstract class ProductDisplay extends View {
    /**
     * The unique identifier of the product to be displayed.
     */
    protected UUID productId;
    /**
     * The controller responsible for shop-related actions.
     */
    protected ShopController shopController;
    /**
     * The controller responsible for profile-related actions.
     */
    protected ProfileController profileController;

    /**
     * Constructs a new {@code ProductDisplay} with the specified product ID, shop controller, and profile controller.
     * This serves as the base constructor for all classes extending {@code ProductDisplay}.
     * It initializes the unique identifier of the product, the shop controller, and the profile controller for further use.
     *
     * @param productId         The unique identifier of the product.
     * @param shopController    The controller responsible for shop-related actions.
     * @param profileController The controller responsible for profile-related actions.
     */
    public ProductDisplay(UUID productId, ShopController shopController, ProfileController profileController) {
        this.productId = productId;
        this.shopController = shopController;
        this.profileController = profileController;
    }

    /**
     * Renders the common product information shared by all products.
     * Displays details such as title, likes, rating, description, category, sub-category, price, bonus fidelity points,
     * promotional discount, promotional bonus fidelity points, promotional end date, seller, and commercialization date.
     *
     * @param product The product for which to render information.
     */
    public void renderProductInfo(Product product) {
        if (product == null) {
            System.out.println(prettify("This product no longer exists."));
            return;
        }

        clearConsole();
        System.out.println(prettify("Title: " + product.getTitle()));
        System.out.println(prettify("Likes: ") + product.getLikes());
        System.out.println(prettify("Rating: ") + product.getRating());
        System.out.println(prettify("Description: ") + product.getDescription());
        System.out.println(prettify("Category: ") + product.getCategory());
        System.out.println(prettify("Sub-category: ") + product.getSubCategory());
        System.out.println(prettify("Price: ") + Utils.formatMoney(product.getPrice()));
        System.out.println(prettify("Fidelity Points: ") + product.getBonusFidelityPoints());

        if (product.getPromoDiscount() != 0) {
            System.out.println(prettify("Promotional discount: ") + Utils.formatMoney(product.getPromoDiscount()));
        }
        if (product.getPromoFidelityPoints() != 0) {
            System.out.println(prettify("Promotional bonus fidelity points: ") + product.getPromoFidelityPoints());
        }
        if (product.getPromoEndDate() != null) {
            System.out.println(prettify("Promotional ends on: ") + product.getPromoEndDate());
        }

        Seller seller = shopController.getSeller(product.getSellerId());
        System.out.println(prettify("Sold by: ") + seller.getName());
        System.out.println(prettify("Remaining stock: " + product.getQuantity()));

        System.out.println(prettify("Commercialization date: ") + product.getCommercializationDate());
    }

    /**
     * Renders the product-specific actions menu, allowing the user to perform actions such as toggling likes, displaying reviews, and adding to the cart.
     * This method serves as a central point for rendering actions based on the user type (Buyer or Seller) and delegates to specific methods accordingly.
     *
     * @param product The product for which to render actions.
     *
     * @return {@code true} if the rendering was successful and the user chose an action, {@code false} otherwise.
     */
    public boolean renderProductActions(Product product) {
        Buyer buyer = profileController.getBuyer();
        if (buyer != null) {
            return displayBuyerActions(product);
        } else {
            return displaySellerActions(product);
        }
    }

    /**
     * Displays buyer-specific actions for a given product, such as toggling likes, displaying reviews, and adding to the cart.
     *
     * @param product The product for which to display buyer actions.
     *
     * @return True if the action was successfully performed; false otherwise.
     */
    public boolean displayBuyerActions(Product product) {
        String[] options = {"Go back", "Toggle like", "Display reviews", "Add to cart"};

        clearConsole();
        int answer = prettyMenu("Select action", options);
        switch (answer) {
            case 0 -> {
                return false;
            }
            case 1 -> {
                OperationResult result = shopController.toggleLike(product.getId());
                System.out.println(prettify(result.message()));
                waitForKey();
                return true;
            }
            case 2 -> {
                shopController.displayReviews(product.getId());
                return false;
            }
            case 3 -> {
                int qty = prettyPromptInt("Quantity", (quantity) -> shopController.validateQuantity(product.getId(), quantity));

                OperationResult result = shopController.addToCart(product.getId(), qty);

                System.out.println(prettify(result.message()));
                waitForKey();
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Displays seller-specific actions for a given product, such as displaying reviews and starting a promotion.
     *
     * @param product The product for which to display seller actions.
     *
     * @return True if the action was successfully performed; false otherwise.
     */
    public boolean displaySellerActions(Product product) {
        ArrayList<DynamicMenuItem> options = new ArrayList<>();

        options.add(new DynamicMenuItem("Display reviews", () -> {
            shopController.displayReviews(product.getId());
        }, () -> true));
        options.add(new DynamicMenuItem("Start promotion", () -> {
            int discount = prettyPromptCurrency("Promotional discount");
            int promoPoints = prettyPromptInt("Promotional fidelity points", points -> validateBonusFidelityPoints(points + product.getBonusFidelityPoints(), product.getPrice()));
            LocalDate endDate = prettyPromptDate("Promotion end date");
            System.out.println(prettify(shopController.startProductPromotion(productId, discount, promoPoints, endDate).message()));
            waitForKey();
        }, () -> product.getSellerId().equals(profileController.getSeller().getId())));

        prettyDynamicMenu("Select action", "Go back", options, () -> {});

        return false;
    }
}
