/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.*;

import java.util.List;
import java.util.UUID;

public class AuthenticationModel {
    private final Database db;

    public AuthenticationModel(Database database) {
        this.db = database;
    }

    public OperationResult authenticateBuyer(String username, String password) {
        List<Buyer> matchedBuyers = this.db.get(DataMap.BUYERS,
                (entry) -> entry.getUsername().equalsIgnoreCase(username) && entry.getPasswordHash() == password.hashCode()
        );

        if (matchedBuyers.size() >= 1) {
            Buyer buyer = matchedBuyers.get(0);
            Session.createSession(buyer.getId(), UserType.Buyer);

            return new OperationResult(true, "");
        }
        else {
            return new OperationResult(false, "Username or password is incorrect.");
        }
    }

    public OperationResult authenticateSeller(String name, String password) {
        List<Seller> matchedSellers = this.db.get(DataMap.SELLERS,
                (entry) -> entry.getName().equalsIgnoreCase(name) && entry.getPasswordHash() == password.hashCode()
        );

        if (matchedSellers.size() >= 1) {
            Seller seller = matchedSellers.get(0);
            Session.createSession(seller.getId(), UserType.Seller);

            return new OperationResult(true, "");
        }
        else {
            return new OperationResult(false, "Username or password is incorrect.");
        }
    }

    public OperationResult registerNewBuyer(String username, String password, String firstName, String lastName, String email, String phoneNumber, String address) {
        // validate parameters ?

        db.add(DataMap.BUYERS, new Buyer(username, password.hashCode(), firstName, lastName, email, phoneNumber, address, 0));

        return new OperationResult(true, "");
    }

    public OperationResult registerNewSeller(String name, String password, String email, String phoneNumber, String address) {
        // validate parameters ?

        db.add(DataMap.SELLERS, new Seller(name, password.hashCode(), email, phoneNumber, address));

        return new OperationResult(true, "");
    }

    public boolean isBuyerNameAvailable(String username) {
        return db.<Buyer>get(DataMap.BUYERS, (buyer) -> buyer.getUsername().equalsIgnoreCase(username)).size() == 0;
    }

    public boolean isSellerNameAvailable(String name) {
        return db.<Seller>get(DataMap.SELLERS, (seller) -> seller.getName().equalsIgnoreCase(name)).size() == 0;
    }

    public boolean isCorrectPassword(UUID userId, String password) {
        User user = db.get(DataMap.BUYERS, userId);
        if (user != null) {
            return user.getPasswordHash() == password.hashCode();
        }

        user = db.get(DataMap.SELLERS, userId);
        if (user != null) {
            return user.getPasswordHash() == password.hashCode();
        }

        return false;
    }
}
