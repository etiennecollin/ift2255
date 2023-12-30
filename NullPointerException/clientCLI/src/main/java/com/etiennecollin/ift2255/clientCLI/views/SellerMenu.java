/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The SellerMenu class represents a view for displaying and managing various actions available to a seller.
 * It includes options such as offering a product, changing order status, displaying notifications,
 * displaying tickets, displaying activities, viewing account information, and logging out.
 */
public class SellerMenu extends View {
    /**
     * The ProfileController used for interacting with profile-related functionalities.
     */
    private final ProfileController profileController;
    /**
     * The ShopController used for managing shop-related functionalities.
     */
    private final ShopController shopController;
    /**
     * The TicketController used for managing ticket-related functionalities.
     */
    private final TicketController ticketController;

    /**
     * Constructs a SellerMenu with the specified ProfileController, ShopController, and TicketController.
     *
     * @param profileController the ProfileController used for interacting with profile-related functionalities.
     * @param shopController    the ShopController used for managing shop-related functionalities.
     * @param ticketController  the TicketController used for managing ticket-related functionalities.
     */
    public SellerMenu(ProfileController profileController, ShopController shopController, TicketController ticketController) {
        this.profileController = profileController;
        this.shopController = shopController;
        this.ticketController = ticketController;
    }

    /**
     * Renders the SellerMenu view, allowing the seller to choose from various actions such as offering a product,
     * changing order status, displaying notifications, displaying tickets, displaying activities,
     * viewing account information, and logging out.
     */
    @Override
    public void render() {
        String[] sellerMenu = {"Offer product", "Change order status", "Display notifications", "Display tickets", "Display activities", "Account information", "Display my products", "Display catalog", "Search for a product", "Find user", "Log out"};

        clearConsole();
        int answer = prettyMenu("Main menu", sellerMenu);
        switch (answer) {
            case 0 -> shopController.displayOfferProduct();
            case 1 -> shopController.displayPendingSellerOrders();
            //                case 2 -> displayNotifications();
            case 3 -> ticketController.displayTickets();
            case 4 -> profileController.displaySellerActivities();
            case 5 -> profileController.displaySellerProfile();
            case 6 -> shopController.displaySellerProducts();
            case 7 -> shopController.displayProducts(null);
            case 8 -> shopController.displayProductSearch();
            case 9 -> profileController.displayUserFinder();
            case 10 -> {
                System.out.println(prettify("Logging-out..."));
                profileController.logout();
            }
        }
    }
}
