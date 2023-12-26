/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.io.Serializable;

/**
 * Represents the types of entities that can receive likes, extending Serializable.
 */
public enum LikeType implements Serializable {
    /**
     * Represents a like on a seller.
     */
    Seller,
    /**
     * Represents a like on a buyer.
     */
    Buyer,
    /**
     * Represents a like on a product.
     */
    Product,
    /**
     * Represents a like on a review.
     */
    Review
}
