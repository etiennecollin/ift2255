/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.model.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.model.data.Seller;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class UserFinder extends View {
    private final ProfileController profileController;

    public UserFinder(ProfileController profileController) {
        this.profileController = profileController;
    }

    @Override
    public void render() {
        String[] options = {"Main menu", "Buyer", "Seller"};
        loop:
        while (true) {
            clearConsole();
            int answer = prettyMenu("Search for", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> findBuyer();
                case 2 -> findSeller();
            }
        }
    }

    private void findBuyer() {
        loop:
        while (true) {
            clearConsole();
            String[] searchBy = {"Go Back", "Name", "Phone number", "email"};
            int search = prettyMenu("Search buyer by", searchBy);

            List<Buyer> matchList = new ArrayList<>();
            switch (search) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    String name = prettyPrompt("Name").toLowerCase();
                    matchList = profileController.searchBuyerName(name);
                }
                case 2 -> {
                    String phoneNumber = prettyPrompt("Phone number");
                    matchList = profileController.searchBuyerPhone(phoneNumber);
                }
                case 3 -> {
                    String email = prettyPrompt("Email").toLowerCase();
                    matchList = profileController.searchBuyerEmail(email);
                }
            }

            profileController.displayBuyers(matchList);
        }
    }

    private void findSeller() {
        loop:
        while (true) {
            clearConsole();
            String[] searchBy = {"Go back", "Name", "Address", "Phone number", "email"};
            int search = prettyMenu("Search seller by", searchBy);

            List<Seller> matchList = new ArrayList<>();
            switch (search) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    String name = prettyPrompt("Name").toLowerCase();
                    matchList = profileController.searchSellerName(name);
                }
                case 2 -> {
                    String address = prettyPrompt("Address").toLowerCase();
                    matchList = profileController.searchSellerAddress(address);
                }
                case 3 -> {
                    String phoneNumber = prettyPrompt("Phone number");
                    matchList = profileController.searchSellerPhone(phoneNumber);
                }
                case 4 -> {
                    String email = prettyPrompt("email").toLowerCase();
                    matchList = profileController.searchSellerEmail(email);
                }
            }

            profileController.displaySellers(matchList);
        }
    }
}
