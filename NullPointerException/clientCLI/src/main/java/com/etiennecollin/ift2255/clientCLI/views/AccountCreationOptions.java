/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class AccountCreationOptions extends View {
    private AuthenticationController authController;

    public AccountCreationOptions(AuthenticationController authController) {
        this.authController = authController;
    }

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
            }
            else {
                if (!prettyPromptBool("Try again?")) break;
            }
        }
        clearConsole();
    }
}
