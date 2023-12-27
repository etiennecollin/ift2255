/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The LoginForm class represents the login form view of the client CLI application.
 * It allows users to log in as either a buyer or a seller, or to go back to the main menu.
 * <p>
 * The class extends the {@link View} class.
 */
public class LoginForm extends View {
    /**
     * The AuthenticationController used for handling user authentication.
     */
    private final AuthenticationController authController;

    /**
     * Constructs a LoginForm with the specified AuthenticationController.
     *
     * @param authController the AuthenticationController used for handling user authentication.
     */
    public LoginForm(AuthenticationController authController) {
        this.authController = authController;
    }

    /**
     * Renders the login form view, allowing the user to choose between logging in as a buyer, a seller, or going back.
     * The view loops until a valid choice is made, and the corresponding action is executed.
     */
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
            } else if (!prettyPromptBool("Try again?")) {
                break;
            }
        }
        clearConsole();
    }
}
