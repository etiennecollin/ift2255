
/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    /**
     * The main method of the `Client` class, which is the entry point for the CLI application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println(prettify("Welcome to UniShop"));

        String[] choices = {"Login", "Register"};
        String answer = choices[prettyMenu("Main menu", choices)];
        if (answer.equals(choices[0])) {
            connexionForm();
        } else if (answer.equals(choices[1])) {
            createAccount();
        }
    }

    private static void connexionForm() {
        System.out.println(prettify("Login menu"));
        while (true) {
            String username = prettyPrompt("Username");
            String password = prettyPrompt("Password");

            if (!true) { // TODO add user info validation
                System.out.println(prettify("The username or password is incorrect"));
                continue;
            }

            break;
        }
        System.out.println(prettify("Successfully logged in"));

        // TODO display the next menu
        String[] menuAcheteur = {"Product catalog", "Search product", "Review a product", "Cart", "Order", "Review previous orders", "Confirm order reception", "Signal product issue", "Return/Exchange", "My activities", "Find seller", "Disconnect"};
        String[] menuRevendeur = {"Offer product", "Modify order status", "My activities", "Manage issues", "Confirm return reception", "Disconnect"};
    }

    private static void createAccount() {
        String[] choices = {"Buyer", "Seller"};
        String accountType = choices[prettyMenu("Select your account type", choices)];
        if (accountType.equals(choices[0])) {
            buyerCreationForm();
        } else if (accountType.equals(choices[1])) {
            sellerCreationForm();
        }

        System.out.println(prettify("Successfully registered"));
    }

    private static void buyerCreationForm() { // TODO return buyer
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String username = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        String phoneNumber = prettyPrompt("Phone number");
        String address = prettyPrompt("Shipping address");
    }

    private static void sellerCreationForm() { // TODO return seller
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String username = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        String phoneNumber = prettyPrompt("Phone number");
        String product = prettyPrompt("Offered product"); // TODO create a product with createProduct()
    }
}
