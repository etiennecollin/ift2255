/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The SessionCartDatabase class implements the Database interface and serves as an in-memory database for storing cart products during a user's session.
 * It provides methods for CRUD operations on cart products.
 */
public class SessionCartDatabase implements Database {
    /**
     * The list of cart products stored in the session database.
     */
    List<CartProduct> cartProducts;

    /**
     * Constructs a new SessionCartDatabase with an empty list of cart products.
     */
    public SessionCartDatabase() {
        cartProducts = new ArrayList<>();
    }

    /**
     * Retrieves a single cart product from the session database based on the provided data map and ID.
     *
     * @param dataMap The data map associated with the cart product.
     * @param id      The ID of the cart product to retrieve.
     * @param <T>     The type of DatabaseObject.
     *
     * @return The cart product with the specified ID, or null if not found.
     */
    @Override
    public <T extends DatabaseObject> T get(DataMap dataMap, UUID id) {
        List<CartProduct> filteredCart = cartProducts.stream().filter((entry) -> entry.getId().equals(id)).toList();
        if (filteredCart.isEmpty()) {
            return null;
        } else {
            return (T) filteredCart.get(0);
        }
    }

    /**
     * Retrieves a list of cart products from the session database based on the provided data map and filter predicate.
     *
     * @param dataMap The data map associated with the cart products.
     * @param filter  The filter predicate to apply.
     * @param <T>     The type of DatabaseObject.
     *
     * @return The list of cart products that match the filter predicate.
     */
    @Override
    public <T extends DatabaseObject> List<T> get(DataMap dataMap, Predicate<T> filter) {
        return ((List<T>) cartProducts).stream().filter(filter).toList();
    }

    /**
     * Adds a single cart product to the session database based on the provided data map.
     *
     * @param dataMap The data map associated with the cart product.
     * @param object  The cart product to add.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the cart product is successfully added.
     */
    @Override
    public <T extends DatabaseObject> boolean add(DataMap dataMap, T object) {
        cartProducts.add((CartProduct) object);
        return true;
    }

    /**
     * Adds a list of cart products to the session database based on the provided data map.
     *
     * @param dataMap The data map associated with the cart products.
     * @param objects The list of cart products to add.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the list of cart products is successfully added.
     */
    @Override
    public <T extends DatabaseObject> boolean add(DataMap dataMap, List<T> objects) {
        cartProducts.addAll((List<? extends CartProduct>) objects);
        return true;
    }

    /**
     * Updates a single cart product in the session database based on the provided data map, update consumer, and ID.
     *
     * @param dataMap The data map associated with the cart product.
     * @param update  The update consumer to apply.
     * @param id      The ID of the cart product to update.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the cart product is successfully updated.
     */
    @Override
    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, UUID id) {
        List<CartProduct> filteredData = cartProducts.stream().filter((entry) -> entry.getId().equals(id)).toList();
        if (!filteredData.isEmpty()) {
            update.accept((T) filteredData.get(0));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates a list of cart products in the session database based on the provided data map, update consumer, and filter predicate.
     *
     * @param dataMap The data map associated with the cart products.
     * @param update  The update consumer to apply.
     * @param filter  The filter predicate to apply.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the list of cart products is successfully updated.
     */
    @Override
    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, Predicate<T> filter) {
        List<T> filteredData = ((List<T>) cartProducts).stream().filter(filter).toList();
        filteredData.forEach(update);
        return true;
    }

    /**
     * Removes a single cart product from the session database based on the provided data map and ID.
     *
     * @param dataMap The data map associated with the cart product.
     * @param id      The ID of the cart product to remove.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the cart product is successfully removed.
     */
    @Override
    public <T extends DatabaseObject> boolean remove(DataMap dataMap, UUID id) {
        cartProducts = (List<CartProduct>) ((List<T>) cartProducts).stream().filter((v) -> v.getId() != id).toList();
        return true;
    }

    /**
     * Removes a list of cart products from the session database based on the provided data map and filter predicate.
     *
     * @param dataMap The data map associated with the cart products.
     * @param filter  The filter predicate to apply.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the list of cart products is successfully removed.
     */
    @Override
    public <T extends DatabaseObject> boolean remove(DataMap dataMap, Predicate<T> filter) {
        cartProducts = (List<CartProduct>) ((List<T>) cartProducts).stream().filter((v) -> !filter.test(v)).toList();
        return true;
    }
}
