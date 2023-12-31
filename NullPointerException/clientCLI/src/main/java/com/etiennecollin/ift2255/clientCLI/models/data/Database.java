/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Interface representing a database with CRUD operations for DatabaseObjects.
 */
public interface Database {
    /**
     * Retrieves a DatabaseObject from the database based on its unique identifier.
     *
     * @param dataMap The data map specifying the type of objects to retrieve.
     * @param id      The unique identifier of the object to retrieve.
     * @param <T>     The type of DatabaseObject.
     *
     * @return The DatabaseObject if found, null otherwise.
     */
    <T extends DatabaseObject> T get(DataMap dataMap, UUID id);

    /**
     * Retrieves a list of DatabaseObjects from the database based on a filter predicate.
     *
     * @param dataMap The data map specifying the type of objects to retrieve.
     * @param filter  The filter predicate to apply.
     * @param <T>     The type of DatabaseObject.
     *
     * @return The list of DatabaseObjects matching the filter.
     */
    <T extends DatabaseObject> List<T> get(DataMap dataMap, Predicate<T> filter);

    /**
     * Adds a DatabaseObject to the database.
     *
     * @param dataMap The data map specifying the type of objects to add.
     * @param object  The DatabaseObject to add.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the addition is successful, false otherwise.
     */
    <T extends DatabaseObject> boolean add(DataMap dataMap, T object);

    /**
     * Adds a list of DatabaseObjects to the database.
     *
     * @param dataMap The data map specifying the type of objects to add.
     * @param objects The list of DatabaseObjects to add.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the addition is successful, false otherwise.
     */
    <T extends DatabaseObject> boolean add(DataMap dataMap, List<T> objects);

    /**
     * Updates a DatabaseObject in the database based on its unique identifier.
     *
     * @param dataMap The data map specifying the type of objects to update.
     * @param update  The consumer function to apply updates to the object.
     * @param id      The unique identifier of the object to update.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the update is successful, false otherwise.
     */
    <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, UUID id);

    /**
     * Updates DatabaseObjects in the database based on a filter predicate.
     *
     * @param dataMap The data map specifying the type of objects to update.
     * @param update  The consumer function to apply updates to the objects.
     * @param filter  The filter predicate to apply.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the update is successful, false otherwise.
     */
    <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, Predicate<T> filter);

    /**
     * Removes a DatabaseObject from the database based on its unique identifier.
     *
     * @param dataMap The data map specifying the type of objects to remove.
     * @param id      The unique identifier of the object to remove.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the removal is successful, false otherwise.
     */
    <T extends DatabaseObject> boolean remove(DataMap dataMap, UUID id);

    /**
     * Removes DatabaseObjects from the database based on a filter predicate.
     *
     * @param dataMap The data map specifying the type of objects to remove.
     * @param filter  The filter predicate to apply.
     * @param <T>     The type of DatabaseObject.
     *
     * @return True if the removal is successful, false otherwise.
     */
    <T extends DatabaseObject> boolean remove(DataMap dataMap, Predicate<T> filter);
}
