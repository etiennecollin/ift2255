/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.models.data.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The {@code ProfileModel} class provides functionality related to user profiles.
 * It includes methods to retrieve, update, and search buyer and seller profiles.
 */
public class ProfileModel {
    /**
     * The database instance used to interact with persistent data.
     */
    private final Database db;

    /**
     * Constructs a {@code ProfileModel} with the specified database instance.
     *
     * @param database The database instance.
     */
    public ProfileModel(Database database) {
        this.db = database;
    }

    /**
     * Retrieves a list of notifications associated with the specified user.
     *
     * @param userId The unique identifier of the user for whom notifications are retrieved.
     *
     * @return A list of notifications associated with the specified user.
     */
    public List<Notification> getNotifications(UUID userId) {
        return db.get(DataMap.NOTIFICATIONS, (n) -> n.getUserId().equals(userId));
    }

    /**
     * Removes a notification with the specified identifier.
     *
     * @param notificationId The unique identifier of the notification to be removed.
     */
    public void removeNotification(UUID notificationId) {
        db.remove(DataMap.NOTIFICATIONS, (n) -> n.getId().equals(notificationId));
    }

    /**
     * Retrieves a list of all notifications in the system.
     *
     * @return A list of all notifications in the system.
     */
    public List<Notification> getAllNotifications() {
        return db.get(DataMap.NOTIFICATIONS, (n) -> true);
    }

    /**
     * Retrieves the buyer profile associated with the specified user ID.
     *
     * @param userId The unique identifier of the buyer.
     *
     * @return The buyer profile or {@code null} if not found.
     */
    public Buyer getBuyer(UUID userId) {
        List<Buyer> buyers = db.get(DataMap.BUYERS, (b) -> b.getId().equals(userId));
        if (!buyers.isEmpty()) {
            return buyers.get(0);
        }

        return null;
    }

    /**
     * Updates the buyer profile with the specified information.
     *
     * @param buyerId   The unique identifier of the buyer.
     * @param firstName The new first name (or {@code null} to keep the current value).
     * @param lastName  The new last name (or {@code null} to keep the current value).
     * @param password  The new password (or {@code null} to keep the current value).
     * @param email     The new email (or {@code null} to keep the current value).
     * @param phone     The new phone number (or {@code null} to keep the current value).
     * @param address   The new address (or {@code null} to keep the current value).
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
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

    /**
     * Searches for buyers based on the specified predicate.
     *
     * @param predicate The predicate to filter buyers.
     *
     * @return A list of buyers matching the predicate.
     */
    public List<Buyer> searchBuyers(Predicate<Buyer> predicate) {
        return db.get(DataMap.BUYERS, predicate);
    }

    /**
     * Retrieves the seller profile associated with the specified user ID.
     *
     * @param userId The unique identifier of the seller.
     *
     * @return The seller profile or {@code null} if not found.
     */
    public Seller getSeller(UUID userId) {
        List<Seller> sellers = db.get(DataMap.SELLERS, (b) -> b.getId().equals(userId));
        if (!sellers.isEmpty()) {
            return sellers.get(0);
        }

        return null;
    }

    /**
     * Updates the seller profile with the specified information.
     *
     * @param sellerId The unique identifier of the seller.
     * @param name     The new name (or {@code null} to keep the current value).
     * @param password The new password (or {@code null} to keep the current value).
     * @param email    The new email (or {@code null} to keep the current value).
     * @param phone    The new phone number (or {@code null} to keep the current value).
     * @param address  The new address (or {@code null} to keep the current value).
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult updateSeller(UUID sellerId, String name, String password, String email, String phone, String address) {
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
        }, sellerId);

        if (result) {
            return new OperationResult(true, "Profile updated.");
        } else {
            return new OperationResult(false, "Profile could not be updated.");
        }
    }

    /**
     * Searches for sellers based on the specified predicate.
     *
     * @param predicate The predicate to filter sellers.
     *
     * @return A list of sellers matching the predicate.
     */
    public List<Seller> searchSellers(Predicate<Seller> predicate) {
        return db.get(DataMap.SELLERS, predicate);
    }

    /**
     * Clears the current user session, effectively logging the user out.
     */
    public void logout() {
        Session.clearSession();
    }
}
