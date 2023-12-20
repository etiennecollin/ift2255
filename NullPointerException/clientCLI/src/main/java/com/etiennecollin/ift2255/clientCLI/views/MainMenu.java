/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class MainMenu extends View {
    private AuthenticationController authController;

    public MainMenu(AuthenticationController authController) {
        this.authController = authController;
    }

    @Override
    public void render() {
        String[] menu = {"Login", "Register", "Quit"};
        while (true) {
            clearConsole();
            int answer = prettyMenu("Welcome to UniShop", menu);

            if (answer == 0) {
                authController.handleLogin();
                break;
//                loginForm();
            } else if (answer == 1) {
                authController.handleAccountCreationOptions();
                break;
//                createAccount();
            } else if (answer == 2) {
                System.out.println(prettify("Quitting UniShop"));
                authController.quitApplication();
                break;
            }
        }
    }
}
