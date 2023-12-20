/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class BuyerMenu extends View {
    private ProfileController profileController;

    public BuyerMenu(ProfileController profileController) {
        this.profileController = profileController;
    }

    @Override
    public void render() {
        String[] buyerMenu = {"Display catalog", "Search a product", "Display cart", "Find user", "Display orders", "Display notifications", "Display tickets", "Display activities", "Update account information", "Log out"};

        loop:
        while (true) {
            clearConsole();
            int buyerAnswer = prettyMenu("Main menu", buyerMenu);
            switch (buyerAnswer) {
//                case 0 -> displayProducts(unishop.getCatalog());
//                case 1 -> searchProduct();
//                case 2 -> displayCart();
//                case 3 -> findUser();
//                case 4 -> displayBuyerOrders();
//                case 5 -> displayNotifications();
//                case 6 -> displayTickets();
//                case 7 -> displayActivities();
//                case 8 -> updateBuyerInfo();
                case 9 -> {
                    System.out.println(prettify("Logging-out..."));
                    profileController.logout();
                    break loop;
                }
            }
        }
    }
}
