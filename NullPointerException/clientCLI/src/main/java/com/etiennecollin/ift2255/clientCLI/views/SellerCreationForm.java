/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The SellerCreationForm class represents a view for creating a new seller account.
 * It prompts the user for necessary information such as name, email, password, phone number, and shipping address.
 */
public class SellerCreationForm extends View {
    /**
     * The AuthenticationController used for managing authentication-related functionalities.
     */
    private final AuthenticationController authController;

    /**
     * Constructs a SellerCreationForm with the specified AuthenticationController.
     *
     * @param authController the AuthenticationController used for managing authentication-related functionalities.
     */
    public SellerCreationForm(AuthenticationController authController) {
        this.authController = authController;
    }

    /**
     * Renders the SellerCreationForm view, prompting the user for information such as name, email, password,
     * phone number, and shipping address to create a new seller account.
     * It continues to prompt the user until a valid account is created or the user chooses not to retry.
     */
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
