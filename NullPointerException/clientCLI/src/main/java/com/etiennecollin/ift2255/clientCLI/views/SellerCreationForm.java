/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class SellerCreationForm extends View {
    private AuthenticationController authController;

    public SellerCreationForm(AuthenticationController authController) {
        this.authController = authController;
    }

    @Override
    public void render() {
        while (true) {
            clearConsole();
            String name = prettyPrompt("Name", Utils::validateNotEmpty);
            String email = prettyPrompt("Email", Utils::validateEmail);
            String password = prettyPrompt("Password", Utils::validateNotEmpty);
            String phoneNumber = prettyPrompt("Phone number", Utils::validatePhoneNumber);
            String address = prettyPrompt("Shipping address", Utils::validateNotEmpty);

            OperationResult result = authController.createNewSeller(name, password, email, phoneNumber, address);
            if (!result.message().isEmpty()) {
                System.out.println(result.message());
            }

            if (result.isValid()) {
                break;
            } else {
                if (!prettyPromptBool("Try again?")) break;
            }
        }
    }
}
