/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.classes.UniShop;
import com.etiennecollin.ift2255.clientCLI.model.ProfileModel;
import com.etiennecollin.ift2255.clientCLI.views.MainMenu;
import com.etiennecollin.ift2255.clientCLI.views.ViewRenderer;

public class ProfileController {
    private final ViewRenderer renderer;
    private final ProfileModel profileModel;

    public ProfileController(ViewRenderer renderer, ProfileModel profileModel) {
        this.renderer = renderer;
        this.profileModel = profileModel;
    }

    public void logout() {
        profileModel.logout();
        renderer.clearViewHistory();
        renderer.addNextView(new MainMenu(UniShop.getInstance().getAuthController()), true);
    }
}
