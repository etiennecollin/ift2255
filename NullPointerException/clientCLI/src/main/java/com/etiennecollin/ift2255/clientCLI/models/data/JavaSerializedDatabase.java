/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import com.etiennecollin.ift2255.clientCLI.Client;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Implementation of the Database interface using Java serialization for data storage.
 */
public class JavaSerializedDatabase implements Database {
    /**
     * The path where the serialized data is stored.
     */
    public final String savePath;

    /**
     * Constructs a JavaSerializedDatabase object.
     */
    public JavaSerializedDatabase() {
        try {
            // Inspired by https://stackoverflow.com/a/3627527
            savePath = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile() + "/";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> matches = data.stream().filter((entry) -> entry.getId() == id).toList();
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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            return data.stream().filter(filter).toList();
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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data == null) {
            data = new ArrayList<>();
        }

        data.add(object);
        try {
            save(data, path);
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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data == null) {
            data = new ArrayList<>();
        }

        data.addAll(objects);
        try {
            save(data, path);
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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter((entry) -> entry.getId() == id).toList();
            if (filteredData.size() > 0) {
                update.accept(filteredData.get(0));
                save(data, path);
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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter(filter).toList();
            if (filteredData.size() > 0) {
                filteredData.forEach(update);
                save(data, path);
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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter((v) -> v.getId() != id).toList();
            if (filteredData.size() > 0) {
                save(filteredData, path);
                return true;
            }
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
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter((v) -> !filter.test(v)).toList();
            if (filteredData.size() > 0) {
                save(filteredData, path);
                return true;
            }
        }

        return false;
    }

    /**
     * Saves the current state of some data to the specified file path.
     *
     * @param data     Some data to store to the file.
     * @param filename The file name to which data needs to be saved.
     * @param <T>      The type of data.
     */
    protected <T> void save(T data, String filename) {
        File file = new File(savePath + filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Could not create the save file");
        }

        try (FileOutputStream outputFile = new FileOutputStream(file, false)) {
            try (ObjectOutputStream output = new ObjectOutputStream(outputFile)) {
                output.writeObject(data);
                output.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save the data");
        }
    }

    /**
     * Loads data from the specified file path.
     *
     * @param filename The file name from which data needs to be loaded.
     * @param <T>      The type of data.
     *
     * @return The data in the file.
     */
    @SuppressWarnings("unchecked")
    protected <T> T load(String filename) {
        try (FileInputStream file = new FileInputStream(savePath + filename)) {
            try (ObjectInputStream input = new ObjectInputStream(file)) {
                return (T) input.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
