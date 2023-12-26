/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The BuyerProfile class represents the view for displaying and updating buyer profile information
 * in the client CLI application. Users can view and modify details such as first name, last name,
 * password, email address, phone number, and address.
 * <p>
 * The class extends the {@link View} class.
 */
public class BuyerProfile extends View {
    /**
     * The ProfileController used for interacting with buyer profiles and related actions.
     */
    private final ProfileController profileController;
    /**
     * The AuthenticationController used for authenticating the buyer and handling password-related actions.
     */
    private final AuthenticationController authController;

    /**
     * Constructs a BuyerProfile view with the specified ProfileController and AuthenticationController.
     *
     * @param profileController the ProfileController used for interacting with buyer profiles and related actions.
     * @param authController    the AuthenticationController used for authenticating the buyer and handling password-related actions.
     */
    public BuyerProfile(ProfileController profileController, AuthenticationController authController) {
        this.profileController = profileController;
        this.authController = authController;
    }

    /**
     * Renders the BuyerProfile view, allowing the user to view and update various aspects of their buyer profile.
     */
    @Override
    public void render() {
        String[] options = new String[]{"Go back", "First name", "Last name", "Password", "Email", "Phone number", "Address"};
        while (true) {
            clearConsole();
            Buyer buyer = profileController.getBuyer();

            System.out.println(prettify("Username: " + buyer.getUsername()));
            System.out.println(prettify("Fidelity points: " + buyer.getFidelityPoints()));
            System.out.println(prettify("First name: " + buyer.getFirstName()));
            System.out.println(prettify("Last name: " + buyer.getLastName()));
            System.out.println(prettify("Email address: " + buyer.getEmail()));
            System.out.println(prettify("Phone number: " + buyer.getPhoneNumber()));
            System.out.println(prettify("Address: " + buyer.getAddress()));

            int answer = prettyMenu("Select the information you'd like to change", options);

            OperationResult result;

            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    String newVal = prettyPrompt("Set a new first name", Utils::validateName);
                    result = profileController.updateBuyer(newVal, null, null, null, null, null);
                    System.out.println(prettify(result.message()));
                }
                case 2 -> {
                    String newVal = prettyPrompt("Set a new last name", Utils::validateName);
                    result = profileController.updateBuyer(null, newVal, null, null, null, null);
                    System.out.println(prettify(result.message()));
                }
                case 3 -> {
                    while (true) {
                        String oldPassword = prettyPrompt("Enter old password", Utils::validateNotEmpty);
                        if (authController.isCorrectPassword(buyer.getId(), oldPassword)) {
                            String newVal = prettyPrompt("Set a new password", Utils::validateNotEmpty);
                            result = profileController.updateBuyer(null, null, newVal, null, null, null);
                            System.out.println(prettify(result.message()));
                            break;
                        } else {
                            System.out.println(prettify("Old password invalid"));
                            if (!prettyPromptBool("Try again?")) break;
                        }
                    }
                }
                case 4 -> {
                    String newVal = prettyPrompt("Set a new email address", Utils::validateEmail);
                    result = profileController.updateBuyer(null, null, null, newVal, null, null);
                    System.out.println(prettify(result.message()));
                }
                case 5 -> {
                    String newVal = prettyPrompt("Set a new phone number", Utils::validatePhoneNumber);
                    result = profileController.updateBuyer(null, null, null, null, newVal, null);
                    System.out.println(prettify(result.message()));
                }
                case 6 -> {
                    String newVal = prettyPrompt("Set a new address", Utils::validateNotEmpty);
                    result = profileController.updateBuyer(null, null, null, null, null, newVal);
                    System.out.println(prettify(result.message()));
                }
            }
        }
    }
}
