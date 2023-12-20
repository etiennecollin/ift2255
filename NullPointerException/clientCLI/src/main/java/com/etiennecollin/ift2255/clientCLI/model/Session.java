/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import java.util.UUID;

/**
 * Manages the ephemeral data for a single user's session. Not really a true Singleton.
 */
public class Session {
    private static Session _instance;
    private final UUID userId;

    private Session(UUID userId) {
        this.userId = userId;
    }

    public static Session getInstance() {
        return _instance;
    }

    public static Session createSession(UUID userId) {
        _instance = new Session(userId);
        return _instance;
    }

    public static void clearSession() {
        _instance = null;
    }

    public UUID getUserId() {
        return this.userId;
    }
}
