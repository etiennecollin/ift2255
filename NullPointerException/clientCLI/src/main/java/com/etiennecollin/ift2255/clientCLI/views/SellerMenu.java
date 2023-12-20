/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class SellerMenu extends View {
    private ProfileController profileController;

    public SellerMenu(ProfileController profileController) {
        this.profileController = profileController;
    }

    @Override
    public void render() {
        String[] sellerMenu = {"Offer product", "Change order status", "Display notifications", "Display tickets", "Display activities", "Update account information", "Log out"};

        loop:
        while (true) {
            clearConsole();
            int answer = prettyMenu("Main menu", sellerMenu);
            switch (answer) {
//                case 0 -> offerProduct();
//                case 1 -> displayPendingSellerOrders();
//                case 2 -> displayNotifications();
//                case 3 -> displayTickets();
//                case 4 -> displayActivities();
//                case 5 -> updateSellerInfo();
                case 6 -> {
                    System.out.println(prettify("Logging-out..."));
                    profileController.logout();
                    break loop;
                }
            }
        }
    }
}
