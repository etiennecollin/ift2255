/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.model.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.model.data.CartProduct;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class OrderPlacement extends View {
    private final ShopController shopController;
    private final ProfileController profileController;

    public OrderPlacement(ShopController shopController, ProfileController profileController) {
        this.shopController = shopController;
        this.profileController = profileController;
    }

    @Override
    public void render() {
        Buyer buyer = profileController.getBuyer();
        var cartProductList = shopController.getCart();

        int totalCost = 0;

        for (Tuple<CartProduct, Product> tuple : cartProductList) {
            int quantity = tuple.first.getQuantity();
            Product product = tuple.second;

            int cost = (product.getPrice() - product.getPromoDiscount()) * quantity;
            totalCost += cost;
        }
        clearConsole();

        System.out.println(prettify("Payment form"));
        String shippingAddress = prettyPrompt("Shipping address");

        int fidelityPointsUsed = 0;
        if (buyer.getFidelityPoints() > 0) {
            boolean doUsePoints = prettyPromptBool("You have " + buyer.getFidelityPoints() + " fidelity points. Do you want to use them?");
            if (doUsePoints) {
                Tuple<Integer, Integer> remainingCostAndPoints = shopController.calculateNewCost(totalCost, buyer.getFidelityPoints());
                System.out.println(prettify("Remaining to pay: " + Utils.formatMoney(remainingCostAndPoints.first)));
            }
        }

        String creditCardName = prettyPrompt("Credit card name");

        String creditCardNumber;
        while (true) {
            creditCardNumber = prettyPrompt("Credit card number");
            if (creditCardNumber.length() == 16 && creditCardNumber.matches("\\d+")) {
                break;
            } else {
                System.out.println(prettify("Invalid credit card number"));
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
        YearMonth expirationDate;
        while (true) {
            try {
                expirationDate = YearMonth.parse(prettyPrompt("Expiration date MMYY"), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(prettify("Invalid expiration date"));
            }
        }

        String cvc;
        while (true) {
            cvc = prettyPrompt("CVC");
            if (cvc.length() == 3 && cvc.matches("\\d+")) {
                break;
            } else {
                System.out.println(prettify("Invalid CVC"));
            }
        }

        // Confirm order
        boolean doOrder = prettyPromptBool("Do you want to place the order?");

        if (!doOrder) {
            System.out.println("Cancelled order.");
            return;
        }

        OperationResult result = shopController.createOrder(buyer.getEmail(), buyer.getPhoneNumber(), shippingAddress, buyer.getAddress(), creditCardName, creditCardNumber, expirationDate, cvc, fidelityPointsUsed);

        System.out.println(prettify(result.message()));
    }
}
