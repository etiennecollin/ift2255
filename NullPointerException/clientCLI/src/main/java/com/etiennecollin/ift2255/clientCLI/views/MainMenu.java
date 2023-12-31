/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The MainMenu class represents the main menu view of the client CLI application.
 * It provides options for logging in, registering a new account, and quitting the application.
 * <p>
 * The class extends the {@link View} class.
 */
public class MainMenu extends View {
    /**
     * The AuthenticationController used for handling user authentication and account creation.
     */
    private final AuthenticationController authController;

    /**
     * Constructs a MainMenu with the specified AuthenticationController.
     *
     * @param authController the AuthenticationController used for handling user authentication and account creation.
     */
    public MainMenu(AuthenticationController authController) {
        this.authController = authController;
    }

    /**
     * Renders the main menu view, allowing the user to choose between login, registration, or quitting the application.
     * The view loops until a valid choice is made, and the corresponding action is executed.
     */
    @Override
    public void render() {
        String[] menu = {"Login", "Register", "Quit"};
        while (true) {
            clearConsole();
            int answer = prettyMenu("Welcome to UniShop", menu);

            if (answer == 0) {
                authController.handleLogin();
                break;
            } else if (answer == 1) {
                authController.handleAccountCreationOptions();
                break;
            } else if (answer == 2) {
                System.out.println(prettify("Quitting UniShop"));
                authController.quitApplication();
                break;
            }
        }
    }
}
