/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;

import java.util.ArrayList;
import java.util.List;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The BuyersDisplay class represents the view for displaying information about buyers in the client CLI application.
 * Users can view details about individual buyers, such as their username, full name, following status, fidelity points,
 * number of reviews written, number of reviews liked, and number of products liked. Users can also toggle the follow status
 * of a buyer.
 * <p>
 * The class extends the {@link View} class.
 */
public class BuyersDisplay extends View {
    /**
     * The ProfileController used for interacting with buyer profiles and related actions.
     */
    private final ProfileController profileController;
    /**
     * The list of buyers to be displayed.
     */
    private final List<Buyer> buyerList;

    /**
     * Constructs a BuyersDisplay view with the specified ProfileController and list of buyers.
     *
     * @param profileController the ProfileController used for interacting with buyer profiles and related actions.
     * @param buyerList         the list of buyers to be displayed.
     */
    public BuyersDisplay(ProfileController profileController, List<Buyer> buyerList) {
        this.profileController = profileController;
        this.buyerList = buyerList;
    }

    /**
     * Renders the BuyersDisplay view, allowing the user to view details about individual buyers, toggle follow status,
     * and go back to the previous menu.
     */
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



            loop:
            while (true) {
                clearConsole();
                Buyer buyer = profileController.getBuyer(buyerList.get(index).getId());
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
