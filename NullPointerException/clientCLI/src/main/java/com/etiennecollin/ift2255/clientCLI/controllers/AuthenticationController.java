/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.models.AuthenticationModel;
import com.etiennecollin.ift2255.clientCLI.views.*;

import java.util.UUID;

/**
 * The AuthenticationController class manages user authentication and account creation within the UniShop CLI application.
 * It collaborates with the ViewRenderer and AuthenticationModel to handle login, account creation, and related actions.
 */
public class AuthenticationController {
    /**
     * The ViewRenderer responsible for rendering views.
     */
    private final ViewRenderer renderer;
    /**
     * The AuthenticationModel responsible for handling authentication and account creation.
     */
    private final AuthenticationModel authModel;

    /**
     * Constructs an AuthenticationController with the specified ViewRenderer and AuthenticationModel.
     *
     * @param renderer  The ViewRenderer responsible for rendering views.
     * @param authModel The AuthenticationModel responsible for handling authentication and account creation.
     */
    public AuthenticationController(ViewRenderer renderer, AuthenticationModel authModel) {
        this.renderer = renderer;
        this.authModel = authModel;
    }

    /**
     * Displays the login form view.
     */
    public void handleLogin() {
        renderer.addNextView(new LoginForm(this), true);
    }

    /**
     * Attempts to authenticate a buyer with the provided username and password.
     *
     * @param username The username of the buyer.
     * @param password The password of the buyer.
     *
     * @return The result of the authentication operation.
     */
    public OperationResult loginBuyer(String username, String password) {
        OperationResult result = authModel.authenticateBuyer(username, password);
        if (result.isValid()) {
            renderer.addNextView(new BuyerMenu(UniShop.getInstance().getProfileController(), UniShop.getInstance().getShopController(), UniShop.getInstance().getTicketController()), true);
        }

        return result;
    }

    /**
     * Attempts to authenticate a seller with the provided name and password.
     *
     * @param name     The name of the seller.
     * @param password The password of the seller.
     *
     * @return The result of the authentication operation.
     */
    public OperationResult loginSeller(String name, String password) {
        OperationResult result = authModel.authenticateSeller(name, password);
        if (result.isValid()) {
            renderer.addNextView(new LoginForm(this), true);
        }

        return result;
    }

    /**
     * Checks if the provided password is correct for the specified user ID.
     *
     * @param userId   The UUID of the user.
     * @param password The password to check.
     *
     * @return {@code true} if the password is correct, {@code false} otherwise.
     */
    public boolean isCorrectPassword(UUID userId, String password) {
        return authModel.isCorrectPassword(userId, password);
    }

    /**
     * Displays the account creation options view.
     */
    public void handleAccountCreationOptions() {
        renderer.addNextView(new AccountCreationOptions(this), false);
    }

    /**
     * Displays the buyer creation form view.
     */
    public void handleBuyerCreation() {
        renderer.addNextView(new BuyerCreationForm(this), true);
    }

    /**
     * Displays the seller creation form view.
     */
    public void handleSellerCreation() {
        renderer.addNextView(new SellerCreationForm(this), true);
    }

    /**
     * Creates a new buyer account with the provided details.
     *
     * @param username    The username of the new buyer.
     * @param password    The password of the new buyer.
     * @param firstName   The first name of the new buyer.
     * @param lastName    The last name of the new buyer.
     * @param email       The email of the new buyer.
     * @param phoneNumber The phone number of the new buyer.
     * @param address     The address of the new buyer.
     *
     * @return The result of the account creation operation.
     */
    public OperationResult createNewBuyer(String username, String password, String firstName, String lastName, String email, String phoneNumber, String address) {
        OperationResult result = authModel.registerNewBuyer(username, password, firstName, lastName, email, phoneNumber, address);
        if (result.isValid()) {
            renderer.clearViewHistory();
            renderer.addNextView(new BuyerMenu(UniShop.getInstance().getProfileController(), UniShop.getInstance().getShopController(), UniShop.getInstance().getTicketController()), true);
        }

        return result;
    }

    /**
     * Creates a new seller account with the provided details.
     *
     * @param name        The name of the new seller.
     * @param password    The password of the new seller.
     * @param email       The email of the new seller.
     * @param phoneNumber The phone number of the new seller.
     * @param address     The address of the new seller.
     *
     * @return The result of the account creation operation.
     */
    public OperationResult createNewSeller(String name, String password, String email, String phoneNumber, String address) {
        OperationResult result = authModel.registerNewSeller(name, password, email, phoneNumber, address);
        if (result.isValid()) {
            renderer.clearViewHistory();
            renderer.addNextView(new SellerMenu(UniShop.getInstance().getProfileController(), UniShop.getInstance().getShopController(), UniShop.getInstance().getTicketController()), true);
        }

        return result;
    }

    /**
     * Quits the application by invoking the quit method from the Utils class and clears the view history in the renderer.
     */
    public void quitApplication() {
        Utils.quit();
        renderer.clearViewHistory();
    }
}
