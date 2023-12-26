/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.model.data.DataMap;
import com.etiennecollin.ift2255.clientCLI.model.data.Database;
import com.etiennecollin.ift2255.clientCLI.model.data.Seller;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class ProfileModel {
    private final Database db;

    public ProfileModel(Database database) {
        this.db = database;
    }

    public Buyer getBuyer(UUID userId) {
        List<Buyer> buyers = db.get(DataMap.BUYERS, (b) -> b.getId() == userId);
        if (buyers.size() >= 1) {
            return buyers.get(0);
        }

        return null;
    }

    public OperationResult updateBuyer(UUID buyerId, String firstName, String lastName, String password, String email, String phone, String address) {
        boolean result = db.<Buyer>update(DataMap.BUYERS, (buyer) -> {
            if (firstName != null) {
                buyer.setFirstName(firstName);
            }
            if (lastName != null) {
                buyer.setLastName(lastName);
            }
            if (password != null) {
                buyer.setPasswordHash(password.hashCode());
            }
            if (email != null) {
                buyer.setEmail(email);
            }
            if (phone != null) {
                buyer.setPhoneNumber(phone);
            }
            if (address != null) {
                buyer.setAddress(address);
            }
        }, buyerId);

        if (result) {
            return new OperationResult(true, "Profile updated.");
        } else {
            return new OperationResult(false, "Profile could not be updated.");
        }
    }

    public List<Buyer> searchBuyers(Predicate<Buyer> predicate) {
        return db.get(DataMap.BUYERS, predicate);
    }

    public Seller getSeller(UUID userId) {
        List<Seller> sellers = db.get(DataMap.SELLERS, (b) -> b.getId() == userId);
        if (sellers.size() >= 1) {
            return sellers.get(0);
        }

        return null;
    }

    public OperationResult updateSeller(UUID buyerId, String name, String password, String email, String phone, String address) {
        boolean result = db.<Seller>update(DataMap.SELLERS, (seller) -> {
            if (name != null) {
                seller.setName(name);
            }
            if (password != null) {
                seller.setPasswordHash(password.hashCode());
            }
            if (email != null) {
                seller.setEmail(email);
            }
            if (phone != null) {
                seller.setPhoneNumber(phone);
            }
            if (address != null) {
                seller.setAddress(address);
            }
        }, buyerId);

        if (result) {
            return new OperationResult(true, "Profile updated.");
        } else {
            return new OperationResult(false, "Profile could not be updated.");
        }
    }

    public List<Seller> searchSellers(Predicate<Seller> predicate) {
        return db.get(DataMap.SELLERS, predicate);
    }

    public void logout() {
        Session.clearSession();
    }
}
