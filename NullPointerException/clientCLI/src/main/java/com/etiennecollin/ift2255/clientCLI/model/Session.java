/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.model.data.UserType;

import java.util.UUID;

/**
 * Manages the ephemeral data for a single user's session. Not really a true Singleton.
 */
public class Session {
    private static Session _instance;
    private final UUID userId;
    private final UserType userType;

    private Session(UUID userId, UserType userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public static Session getInstance() {
        return _instance;
    }

    public static Session createSession(UUID userId, UserType userType) {
        _instance = new Session(userId, userType);
        return _instance;
    }

    public static void clearSession() {
        _instance = null;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public UserType getUserType() {
        return this.userType;
    }
}
