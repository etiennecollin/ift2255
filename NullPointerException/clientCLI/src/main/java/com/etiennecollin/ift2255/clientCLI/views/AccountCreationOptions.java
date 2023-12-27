/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code AccountCreationOptions} class represents a view for choosing the type of account to create in the CLI application.
 * It allows users to select either a buyer or a seller account creation.
 * <p>
 * The class extends the {@link View} class.
 * <p>
 * The user is presented with a menu displaying the available account creation options, namely "Buyer" and "Seller".
 * The choice is processed using the {@link AuthenticationController}, which handles the respective account creation logic.
 */
public class AccountCreationOptions extends View {
    /**
     * The {@link AuthenticationController} for handling account creation options.
     */
    private final AuthenticationController authController;

    /**
     * Constructs a new {@code AccountCreationOptions} instance with the specified {@link AuthenticationController}.
     *
     * @param authController The {@link AuthenticationController} for handling account creation options.
     */
    public AccountCreationOptions(AuthenticationController authController) {
        this.authController = authController;
    }

    /**
     * Renders the view for choosing the type of account to create. The user is prompted to select either a buyer or a seller account.
     * The choice is then processed by the {@link AuthenticationController}, and the corresponding account creation logic is invoked.
     * The process can be repeated if the user chooses to try again.
     */
    @Override
    public void render() {
        while (true) {
            clearConsole();
            System.out.println(prettify("Account creation menu"));
            String[] options = {"Buyer", "Seller"};
            int answer = prettyMenu("What type of account would you like to create?", options);

            if (answer == 0) {
                authController.handleBuyerCreation();
                break;
            } else if (answer == 1) {
                authController.handleSellerCreation();
                break;
            } else {
                if (!prettyPromptBool("Try again?")) break;
            }
        }
        clearConsole();
    }
}
