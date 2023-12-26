/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class BuyerMenu extends View {
    private final ProfileController profileController;
    private final ShopController shopController;
    private final TicketController ticketController;

    public BuyerMenu(ProfileController profileController, ShopController shopController, TicketController ticketController) {
        this.profileController = profileController;
        this.shopController = shopController;
        this.ticketController = ticketController;
    }

    @Override
    public void render() {
        String[] buyerMenu = {"Display catalog", "Search a product", "Display cart", "Find user", "Display orders", "Display notifications", "Display tickets", "Display activities", "Account information", "Log out"};

        clearConsole();
        int buyerAnswer = prettyMenu("Main menu", buyerMenu);
        switch (buyerAnswer) {
            case 0 -> shopController.displayProducts(null);
            case 1 -> shopController.displayProductSearch();
            case 2 -> shopController.displayCart();
            case 3 -> profileController.displayUserFinder();
            case 4 -> shopController.displayBuyerOrdersMenu();
//            case 5 -> displayNotifications();
            case 6 -> ticketController.displayTickets();
//            case 7 -> displayActivities();
            case 8 -> profileController.displayBuyerProfile();
            case 9 -> {
                System.out.println(prettify("Logging-out..."));
                profileController.logout();
            }
        }
    }
}
