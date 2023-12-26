/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.util.UUID;

/**
 * Represents a product in a user's shopping cart.
 * Each instance of this class corresponds to a specific product in a specific buyer's cart.
 */
public class CartProduct extends DatabaseObject {
    /**
     * The unique identifier of the buyer associated with this cart product.
     */
    private final UUID buyerId;
    /**
     * The unique identifier of the product associated with this cart product.
     */
    private final UUID productId;
    /**
     * The quantity of the product in the cart.
     */
    private int quantity;

    /**
     * Constructs a new CartProduct with the specified buyer, product, and quantity.
     *
     * @param buyerId   The unique identifier of the buyer.
     * @param productId The unique identifier of the product.
     * @param quantity  The quantity of the product in the cart.
     */
    public CartProduct(UUID buyerId, UUID productId, int quantity) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Gets the unique identifier of the buyer associated with this cart product.
     *
     * @return The buyer's unique identifier.
     */
    public UUID getBuyerId() {
        return buyerId;
    }

    /**
     * Gets the unique identifier of the product associated with this cart product.
     *
     * @return The product's unique identifier.
     */
    public UUID getProductId() {
        return productId;
    }

    /**
     * Gets the quantity of the product in the cart.
     *
     * @return The quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in the cart.
     *
     * @param quantity The new quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
