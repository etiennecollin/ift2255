/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class LoginForm extends View {
    private final AuthenticationController authController;

    public LoginForm(AuthenticationController authController) {
        this.authController = authController;
    }

    @Override
    public void render() {
        while (true) {
            clearConsole();
            System.out.println(prettify("Login menu"));
            String[] options = {"Buyer", "Seller", "Back"};
            int answer = prettyMenu("Login as", options);

            OperationResult result;
            if (answer == 0) {
                String username = prettyPrompt("Username");
                String password = prettyPrompt("Password");
                result = authController.loginBuyer(username, password);
            } else if (answer == 1) {
                String username = prettyPrompt("Name");
                String password = prettyPrompt("Password");
                result = authController.loginSeller(username, password);
            } else {
                break;
            }

            if (result.isValid()) {
                break;
            }
            else if (!prettyPromptBool("Try again?")) {
                break;
            }
        }
        clearConsole();
    }
}
