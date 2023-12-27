/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code BuyerMenu} class represents the main menu for a buyer user in the CLI application.
 * It provides options for displaying the catalog, searching for a product, managing the cart, finding other users,
 * viewing orders, notifications, tickets, and activities, checking account information, and logging out.
 * <p>
 * The class extends the {@link View} class.
 */
public class BuyerMenu extends View {
    /**
     * The {@link ProfileController} for managing user profiles.
     */
    private final ProfileController profileController;
    /**
     * The {@link ShopController} for managing shopping-related functionality.
     */
    private final ShopController shopController;
    /**
     * The {@link TicketController} for managing tickets.
     */
    private final TicketController ticketController;

    /**
     * Constructs a new {@code BuyerMenu} instance with the specified controllers.
     *
     * @param profileController The {@link ProfileController} for managing user profiles.
     * @param shopController    The {@link ShopController} for managing shopping-related functionality.
     * @param ticketController  The {@link TicketController} for managing tickets.
     */
    public BuyerMenu(ProfileController profileController, ShopController shopController, TicketController ticketController) {
        this.profileController = profileController;
        this.shopController = shopController;
        this.ticketController = ticketController;
    }

    /**
     * Renders the main menu for a buyer user, allowing them to choose from various options.
     * The user's choice is processed to perform the corresponding action.
     */
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
