/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.models.data.DataMap;
import com.etiennecollin.ift2255.clientCLI.models.data.Database;
import com.etiennecollin.ift2255.clientCLI.models.data.DatabaseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockDatabase implements Database {
    HashMap<String, List<DatabaseObject>> db = new HashMap<>();

    /**
     * Gets a specific object from the database based on its ID.
     *
     * @param dataMap The DataMap for the type of object.
     * @param id      The ID of the object.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return The object with the specified ID, or null if not found.
     */
    public <T extends DatabaseObject> T get(DataMap dataMap, UUID id) {
        List<T> data = (List<T>) getData(dataMap);
        if (data != null) {
            List<T> matches = data.stream().filter((entry) -> entry.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
            if (matches.size() > 0) {
                return matches.get(0);
            }
        }

        return null;
    }

    /**
     * Gets a list of objects from the database based on a filter.
     *
     * @param dataMap The DataMap for the type of object.
     * @param filter  The filter predicate.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return A list of objects that match the filter.
     */
    public <T extends DatabaseObject> List<T> get(DataMap dataMap, Predicate<T> filter) {
        List<T> data = (List<T>) getData(dataMap);
        if (data != null) {
            return data.stream().filter(filter).collect(Collectors.toCollection(ArrayList::new));
        }

        return new ArrayList<>();
    }

    /**
     * Adds a single object to the database.
     *
     * @param dataMap The DataMap for the type of object.
     * @param object  The object to add.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return True if the addition was successful, false otherwise.
     */
    public <T extends DatabaseObject> boolean add(DataMap dataMap, T object) {
        List<T> data = (List<T>) getData(dataMap);
        if (data == null) {
            data = new ArrayList<>();
        }

        data.add(object);
        try {
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Adds a list of objects to the database.
     *
     * @param dataMap The DataMap for the type of object.
     * @param objects The list of objects to add.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return True if the addition was successful, false otherwise.
     */
    public <T extends DatabaseObject> boolean add(DataMap dataMap, List<T> objects) {
        List<T> data = (List<T>) getData(dataMap);
        if (data == null) {
            data = new ArrayList<>();
        }

        data.addAll(objects);
        try {
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Updates a single object in the database.
     *
     * @param dataMap The DataMap for the type of object.
     * @param update  The update operation to perform on the object.
     * @param id      The ID of the object to update.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return True if the update was successful, false otherwise.
     */
    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, UUID id) {
        List<T> data = (List<T>) getData(dataMap);
        if (data != null) {
            List<T> filteredData = data.stream().filter((entry) -> entry.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
            if (!filteredData.isEmpty()) {
                update.accept(filteredData.get(0));
                return true;
            }
        }

        return false;
    }

    /**
     * Updates multiple objects in the database based on a filter.
     *
     * @param dataMap The DataMap for the type of object.
     * @param update  The update operation to perform on matching objects.
     * @param filter  The filter predicate.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return True if the update was successful, false otherwise.
     */
    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, Predicate<T> filter) {
        List<T> data = (List<T>) getData(dataMap);
        if (data != null) {
            List<T> filteredData = data.stream().filter(filter).collect(Collectors.toCollection(ArrayList::new));
            if (!filteredData.isEmpty()) {
                filteredData.forEach(update);
                return true;
            }
        }

        return false;
    }

    /**
     * Removes a single object from the database based on its ID.
     *
     * @param dataMap The DataMap for the type of object.
     * @param id      The ID of the object to remove.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return True if the removal was successful, false otherwise.
     */
    public <T extends DatabaseObject> boolean remove(DataMap dataMap, UUID id) {
        List<T> data = (List<T>) getData(dataMap);
        if (data != null) {
            List<T> filteredData = data.stream().filter((v) -> !v.getId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
            return data.size() > filteredData.size();
        }

        return false;
    }

    /**
     * Removes multiple objects from the database based on a filter.
     *
     * @param dataMap The DataMap for the type of object.
     * @param filter  The filter predicate.
     * @param <T>     The type of object which extends {@link DatabaseObject}.
     *
     * @return True if the removal was successful, false otherwise.
     */
    public <T extends DatabaseObject> boolean remove(DataMap dataMap, Predicate<T> filter) {
        List<T> data = (List<T>) getData(dataMap);
        if (data != null) {
            List<T> filteredData = data.stream().filter((v) -> !filter.test(v)).collect(Collectors.toCollection(ArrayList::new));
            return data.size() > filteredData.size();
        }

        return false;
    }

    private List<DatabaseObject> getData(DataMap dataMap) {
        List<DatabaseObject> data = db.get(dataMap.name());

        if (data == null) {
            data = new ArrayList<>();
            db.put(dataMap.name(), data);
        }

        return data;
    }
}
