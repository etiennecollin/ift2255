/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.util.UUID;

/**
 * Represents a like given by a user to a specific entity, extending DatabaseObject.
 */
public class Like extends DatabaseObject {
    //
    /**
     * The unique identifier of the entity that has been liked.
     * <p>
     * Liked Entity could be a buyer, a seller, a product, etc.
     */
    private final UUID likedEntityId;
    /**
     * The unique identifier of the user who gave the like.
     */
    private final UUID userId;
    /**
     * The type of entity that has been liked (e.g., Seller, Buyer, Product, Review).
     */
    private final LikeType likeType;

    /**
     * Constructs a new Like object.
     *
     * @param likedEntityId The unique identifier of the entity that has been liked.
     * @param userId        The unique identifier of the user who gave the like.
     * @param likeType      The type of entity that has been liked.
     */
    public Like(UUID likedEntityId, UUID userId, LikeType likeType) {
        super();
        this.likedEntityId = likedEntityId;
        this.userId = userId;
        this.likeType = likeType;
    }

    /**
     * Gets the unique identifier of the entity that has been liked.
     *
     * @return The liked entity's unique identifier.
     */
    public UUID getLikedEntityId() {
        return likedEntityId;
    }

    /**
     * Gets the unique identifier of the user who gave the like.
     *
     * @return The user's unique identifier.
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Gets the type of entity that has been liked.
     *
     * @return The type of entity liked (e.g., Seller, Buyer, Product, Review).
     */
    public LikeType getLikeType() {
        return likeType;
    }
}
