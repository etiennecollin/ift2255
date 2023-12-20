/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.classes.UniShop;
import com.etiennecollin.ift2255.clientCLI.model.AuthenticationModel;
import com.etiennecollin.ift2255.clientCLI.views.*;

public class AuthenticationController {
    private ViewRenderer renderer;
    private AuthenticationModel authModel;

    public AuthenticationController(ViewRenderer renderer, AuthenticationModel authModel) {
        this.renderer = renderer;
        this.authModel = authModel;
    }

    public void handleLogin() {
        renderer.addNextView(new LoginForm(this), true);
    }

    public OperationResult loginBuyer(String username, String password) {
        OperationResult result = authModel.authenticateBuyer(username, password);
        if (result.isValid()) {
            renderer.addNextView(new BuyerMenu(UniShop.getInstance().getProfileController()), true);
        }

        return result;
    }

    public OperationResult loginSeller(String name, String password) {
        OperationResult result = authModel.authenticateSeller(name, password);
        if (result.isValid()) {
            renderer.addNextView(new LoginForm(this), true);
        }

        return result;
    }

    public void handleAccountCreationOptions() {
        renderer.addNextView(new AccountCreationOptions(this), false);
    }

    public void handleBuyerCreation() {
        renderer.addNextView(new BuyerCreationForm(this), true);
    }

    public void handleSellerCreation() {
        renderer.addNextView(new SellerCreationForm(this), true);
    }

    public OperationResult createNewBuyer(String username, String password, String firstName, String lastName, String email, String phoneNumber, String address) {
        OperationResult result = authModel.registerNewBuyer(username, password, firstName, lastName, email, phoneNumber, address);
        if (result.isValid()) {
            renderer.clearViewHistory();
            renderer.addNextView(new BuyerMenu(UniShop.getInstance().getProfileController()), true);
        }

        return result;
    }

    public OperationResult createNewSeller(String name, String password, String email, String phoneNumber, String address) {
        OperationResult result = authModel.registerNewSeller(name, password, email, phoneNumber, address);
        if (result.isValid()) {
            renderer.clearViewHistory();
            renderer.addNextView(new SellerMenu(UniShop.getInstance().getProfileController()), true);
        }

        return result;
    }

    public void quitApplication() {
        Utils.quit();
        renderer.clearViewHistory();
    }
}
