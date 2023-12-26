/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code BuyerCreationForm} class represents a view for creating a new buyer account in the CLI application.
 * It allows users to enter their personal information such as first name, last name, username, email, password,
 * phone number, and shipping address.
 * <p>
 * The class extends the {@link View} class.
 * <p>
 * The user input is validated using utility methods from the {@link Utils} class. The class holds a reference
 * to an {@link AuthenticationController} for handling the creation of a new buyer account.
 */
public class BuyerCreationForm extends View {
    /**
     * The {@link AuthenticationController} for handling the creation of a new buyer account.
     */
    private final AuthenticationController authController;

    /**
     * Constructs a new {@code BuyerCreationForm} instance with the specified {@link AuthenticationController}.
     *
     * @param authController The {@link AuthenticationController} for handling the creation of a new buyer account.
     */
    public BuyerCreationForm(AuthenticationController authController) {
        this.authController = authController;
    }

    /**
     * Renders the view for creating a new buyer account. The user is prompted to enter their personal information,
     * and the input is validated. If the entered information is valid, the new buyer account is created.
     * The process can be repeated if the user chooses to try again.
     */
    @Override
    public void render() {
        while (true) {
            clearConsole();
            String firstName = prettyPrompt("First name", Utils::validateName);
            String lastName = prettyPrompt("Last name", Utils::validateName);
            String username = prettyPrompt("Username", Utils::validateNotEmpty);
            String email = prettyPrompt("Email", Utils::validateEmail);
            String password = prettyPrompt("Password", Utils::validateNotEmpty);
            String phoneNumber = prettyPrompt("Phone number", Utils::validatePhoneNumber);
            String address = prettyPrompt("Shipping address", Utils::validateNotEmpty);

            OperationResult result = authController.createNewBuyer(username, password, firstName, lastName, email, phoneNumber, address);

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
