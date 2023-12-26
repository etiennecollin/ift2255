/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class SellerMenu extends View {
    private final ProfileController profileController;
    private final ShopController shopController;
    private final TicketController ticketController;

    public SellerMenu(ProfileController profileController, ShopController shopController, TicketController ticketController) {
        this.profileController = profileController;
        this.shopController = shopController;
        this.ticketController = ticketController;
    }

    @Override
    public void render() {
        String[] sellerMenu = {"Offer product", "Change order status", "Display notifications", "Display tickets", "Display activities", "Account information", "Log out"};

        clearConsole();
        int answer = prettyMenu("Main menu", sellerMenu);
        switch (answer) {
            case 0 -> shopController.displayOfferProduct();
            case 1 -> shopController.displayPendingSellerOrders();
//                case 2 -> displayNotifications();
            case 3 -> ticketController.displayTickets();
//                case 4 -> displayActivities();
            case 5 -> profileController.displaySellerProfile();
            case 6 -> {
                System.out.println(prettify("Logging-out..."));
                profileController.logout();
            }
        }
    }
}
