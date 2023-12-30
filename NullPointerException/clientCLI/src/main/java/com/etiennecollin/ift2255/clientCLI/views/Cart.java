/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.models.data.CartProduct;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The Cart class represents the view for managing the shopping cart in the client CLI application.
 * Users can view the products in their cart, remove products, place an order, or empty the entire cart.
 * <p>
 * The class extends the {@link View} class.
 */
public class Cart extends View {
    /**
     * The ShopController used for interacting with the shopping cart and order placement.
     */
    private final ShopController shopController;

    /**
     * Constructs a Cart view with the specified ShopController.
     *
     * @param shopController the ShopController used for interacting with the shopping cart and order placement.
     */
    public Cart(ShopController shopController) {
        this.shopController = shopController;
    }

    /**
     * Renders the cart view, allowing the user to view, manage, and perform actions on the products in their cart.
     * The view includes options to go back to the main menu, remove a product, place an order, or empty the entire cart.
     */
    @Override
    public void render() {
        while (true) {
            clearConsole();
            System.out.println(prettify("My cart: "));
            List<Tuple<CartProduct, Product>> cartProductList = shopController.getCart();

            if (cartProductList.isEmpty()) {
                System.out.println(prettify("Your cart is empty."));
                waitForKey();
                break;
            }

            int totalCost = 0;

            for (Tuple<CartProduct, Product> tuple : cartProductList) {
                int quantity = tuple.first.getQuantity();
                Product product = tuple.second;

                int cost = (product.getPrice() - product.getPromoDiscount()) * quantity;
                totalCost += cost;

                System.out.println(prettify(product.getTitle() + " x" + quantity + " | Cost: " + Utils.formatMoney(cost)));
            }
            System.out.println(prettify("Total: " + Utils.formatMoney(totalCost)));

            String[] options = {"Main menu", "Remove product", "Place order", "Empty cart"};
            int answer = prettyMenu("Cart menu", options);
            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    ArrayList<String> productsString = new ArrayList<>();

                    for (Tuple<CartProduct, Product> tuple : cartProductList) {
                        int quantity = tuple.first.getQuantity();
                        Product product = tuple.second;

                        int cost = (product.getPrice() - product.getPromoDiscount()) * quantity;

                        productsString.add(product.getTitle() + " x" + quantity + " | Cost: " + Utils.formatMoney(cost));
                    }

                    int productToRemove = prettyMenu("What product do you want to remove?", productsString);
                    Tuple<CartProduct, Product> cartProductTuple = cartProductList.get(productToRemove);

                    while (true) {
                        int removeHowMany = prettyPromptInt("How many do you want to remove? (-1 to remove all)");
                        if (removeHowMany == -1 || removeHowMany >= cartProductList.get(productToRemove).first.getQuantity()) {
                            shopController.removeFromCart(cartProductTuple.first.getId(), cartProductTuple.second.getQuantity());
                        } else if (removeHowMany > 0) {
                            shopController.removeFromCart(cartProductTuple.first.getId(), removeHowMany);
                        } else {
                            System.out.println(prettify("Invalid quantity"));

                            if (prettyPromptBool("Try again?")) continue;
                        }
                        System.out.println(prettify("Product successfully removed"));
                        waitForKey();
                        break;
                    }
                }
                case 2 -> {
                    shopController.displayOrderPlacement();
                    return;
                }
                case 3 -> {
                    OperationResult result = shopController.emptyCart();
                    System.out.println(prettify(result.message()));

                    waitForKey();
                }
            }
        }
    }
}
