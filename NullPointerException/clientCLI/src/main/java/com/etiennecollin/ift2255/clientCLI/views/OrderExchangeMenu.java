/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.models.data.CartProduct;
import com.etiennecollin.ift2255.clientCLI.models.data.Ticket;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The OrderExchangeMenu class represents a view for handling order exchanges in the client CLI.
 * Users can view products to return, replacement products, and the associated financial details.
 * Additionally, users can choose to complete the exchange, add more items, or cancel the exchange.
 */
public class OrderExchangeMenu extends View {
    /**
     * The controller responsible for handling tickets.
     */
    private final TicketController ticketController;
    /**
     * The controller responsible for handling shop-related operations.
     */
    private final ShopController shopController;

    /**
     * Constructs a new OrderExchangeMenu with the specified ticket controller and shop controller.
     *
     * @param ticketController The controller responsible for handling tickets.
     * @param shopController   The controller responsible for handling shop-related operations.
     */
    public OrderExchangeMenu(TicketController ticketController, ShopController shopController) {
        this.ticketController = ticketController;
        this.shopController = shopController;
    }

    /**
     * Renders the order exchange menu, allowing users to view products to return, replacement products,
     * and associated financial details. Users can choose to complete the exchange, add more items, or cancel the exchange.
     */
    @Override
    public void render() {
        clearConsole();

        Ticket ticket = ticketController.getExchangeTicket();

        int totalReturnValue = 0;
        System.out.println(prettify("Products to return: "));
        for (Tuple<Product, Integer> productTuple : ticket.getProducts()) {
            System.out.println(prettify(productTuple.second + "x " + productTuple.first.getTitle()));
            totalReturnValue += (productTuple.first.getPrice() - productTuple.first.getPromoDiscount()) * productTuple.second;
        }
        System.out.println(prettify("Return total: " + formatMoney(totalReturnValue)));

        List<CartProduct> cartProductList = ticketController.getExchangeCart();

        int totalReplacementValue = 0;
        System.out.println(prettify("Replacement products: "));
        for (CartProduct cartProduct : cartProductList) {
            Product product = shopController.getProduct(Product.class, cartProduct.getProductId());
            if (product != null) {
                System.out.println(prettify(cartProduct.getQuantity() + "x " + product.getTitle()));
                totalReturnValue += (product.getPrice() - product.getPromoDiscount()) * cartProduct.getQuantity();
            }
        }
        System.out.println(prettify("Replacement total: " + formatMoney(totalReplacementValue)));

        if (totalReplacementValue > totalReturnValue) {
            System.out.println(prettify("Your credit card will be charged " + formatMoney(totalReplacementValue - totalReturnValue)));
        } else {
            System.out.println(prettify("Your credit card and fidelity will be refunded the equivalent of " + formatMoney(totalReturnValue - totalReplacementValue)));
        }

        String[] options = {"Complete exchange", "Add more items", "Cancel exchange"};

        int answer = prettyMenu("Select action", options);
        switch (answer) {
            case 0 -> {
                ticketController.completeExchangeProcess();
            }
            case 1 -> {
                shopController.displayProducts(null);
            }
            case 2 -> {
            }
        }
    }
}
