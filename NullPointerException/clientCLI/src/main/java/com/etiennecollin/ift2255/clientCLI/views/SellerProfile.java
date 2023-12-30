/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.models.data.Seller;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The SellerProfile class represents a view for managing and updating a seller's profile information.
 * It allows the seller to change their name, password, email, phone number, and address.
 */
public class SellerProfile extends View {
    /**
     * The ProfileController used for interacting with profile-related functionalities.
     */
    private final ProfileController profileController;
    /**
     * The AuthenticationController used for authentication and authorization.
     */
    private final AuthenticationController authController;

    /**
     * Constructs a SellerProfile with the specified ProfileController and AuthenticationController.
     *
     * @param profileController the ProfileController used for interacting with profile-related functionalities.
     * @param authController    the AuthenticationController used for authentication and authorization.
     */
    public SellerProfile(ProfileController profileController, AuthenticationController authController) {
        this.profileController = profileController;
        this.authController = authController;
    }

    /**
     * Renders the SellerProfile view, allowing the seller to update various aspects of their profile.
     */
    @Override
    public void render() {
        String[] options = new String[]{"Go back", "Name", "Password", "Email", "Phone number", "Address"};
        while (true) {
            clearConsole();
            Seller seller = profileController.getSeller();

            System.out.println(prettify("Name: " + seller.getName()));
            System.out.println(prettify("Email address: " + seller.getEmail()));
            System.out.println(prettify("Phone number: " + seller.getPhoneNumber()));
            System.out.println(prettify("Address: " + seller.getAddress()));

            int answer = prettyMenu("Select the information you'd like to change", options);

            OperationResult result;

            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    String newVal = prettyPrompt("Set a new name", Utils::validateName);
                    result = profileController.updateSeller(newVal, null, null, null, null);
                    System.out.println(prettify(result.message()));
                    waitForKey();
                }
                case 2 -> {
                    while (true) {
                        String oldPassword = prettyPrompt("Enter old password", Utils::validateNotEmpty);
                        if (authController.isCorrectPassword(seller.getId(), oldPassword)) {
                            String newVal = prettyPrompt("Set a new password", Utils::validateNotEmpty);
                            result = profileController.updateSeller(null, newVal, null, null, null);
                            System.out.println(prettify(result.message()));
                            waitForKey();
                            break;
                        } else {
                            System.out.println(prettify("Old password invalid"));
                            if (!prettyPromptBool("Try again?")) break;
                        }
                    }
                }
                case 3 -> {
                    String newVal = prettyPrompt("Set a new email address", Utils::validateEmail);
                    result = profileController.updateSeller(null, null, newVal, null, null);
                    System.out.println(prettify(result.message()));
                    waitForKey();
                }
                case 4 -> {
                    String newVal = prettyPrompt("Set a new phone number", Utils::validatePhoneNumber);
                    result = profileController.updateSeller(null, null, null, newVal, null);
                    System.out.println(prettify(result.message()));
                    waitForKey();
                }
                case 5 -> {
                    String newVal = prettyPrompt("Set a new address", Utils::validateNotEmpty);
                    result = profileController.updateSeller(null, null, null, null, newVal);
                    System.out.println(prettify(result.message()));
                    waitForKey();
                }
            }
        }
    }
}
