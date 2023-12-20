/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.model.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.model.data.DataMap;
import com.etiennecollin.ift2255.clientCLI.model.data.Database;

import java.util.List;
import java.util.UUID;

public class ProfileModel {
    private final Database db;

    public ProfileModel(Database database) {
        this.db = database;
    }

    public Buyer getBuyer(UUID userId) {
        List<Buyer> buyers = db.<Buyer>get(DataMap.BUYERS, (b) -> b.getId() == userId);
        if (buyers.size() >= 1) {
            return buyers.get(0);
        }

        return null;
    }

    public void logout() {
        Session.clearSession();
    }
}
