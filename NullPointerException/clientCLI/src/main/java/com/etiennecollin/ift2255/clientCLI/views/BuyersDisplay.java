/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.model.data.Buyer;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

public class BuyersDisplay extends View {
    private final ProfileController profileController;
    private final List<Buyer> buyerList;

    public BuyersDisplay(ProfileController profileController, List<Buyer> buyerList) {
        this.profileController = profileController;
        this.buyerList = buyerList;
    }

    @Override
    public void render() {
        Buyer user = profileController.getBuyer();
        ArrayList<String> matchListString = new ArrayList<>();

        for (Buyer buyer : buyerList) {
            matchListString.add(buyer.getUsername());
        }

        matchListString.add("Go back");
        while (true) {
            if (buyerList.isEmpty()) {
                System.out.println("------------");
                System.out.println(prettify("No match found"));
                waitForKey();
                break;
            }
            int index = prettyMenu("Select buyer", matchListString);
            if (index == matchListString.size() - 1) break;

            Buyer buyer = buyerList.get(index);

            loop:
            while (true) {
                clearConsole();
                System.out.println(prettify("Username: " + buyer.getUsername()));
                System.out.println(prettify("Full name: ") + buyer.getFirstName() + " " + buyer.getLastName());
                System.out.println(prettify("Followed by you: " + profileController.isLiked(buyer.getId())));
                System.out.println(prettify("Follows you: " + profileController.isLiked(user.getId(), buyer.getId())));
                System.out.println(prettify("Number of fidelity points: " + buyer.getFidelityPoints()));

                int numReviewsWritten = profileController.getReviewsByAuthor(buyer.getId()).size();
                System.out.println(prettify("Number of reviews written: " + numReviewsWritten));

                int numReviewsLiked = profileController.getReviewLikesByBuyer(buyer.getId()).size();
                System.out.println(prettify("Number of reviews liked: " + numReviewsLiked));

                int numProductsLiked = profileController.getProductLikesByBuyer(buyer.getId()).size();
                System.out.println(prettify("Number of products liked: " + numProductsLiked));
//                System.out.println(prettify("Number of order bought: " + buyer.getOrders().size()));

                String[] options = {"Go back", "Toggle follow"};
                int answer = prettyMenu("Select action", options);
                switch (answer) {
                    case 0 -> {
                        break loop;
                    }
                    case 1 -> {
                        profileController.toggleFollowBuyer(buyer.getId());
                        System.out.println(prettify("Successfully toggled follow"));
                        waitForKey();
                    }
                }
            }
        }
    }
}
