/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.util.UUID;

public class Like extends DatabaseObject {
    // Liked Entity could be a buyer, seller, or product.
    private final UUID likedEntityId;
    private final UUID userId;

    public Like(UUID likedEntityId, UUID userId) {
        super();
        this.likedEntityId = likedEntityId;
        this.userId = userId;
    }

    public UUID getLikedEntityId() {
        return likedEntityId;
    }

    public UUID getUserId() {
        return userId;
    }
}
