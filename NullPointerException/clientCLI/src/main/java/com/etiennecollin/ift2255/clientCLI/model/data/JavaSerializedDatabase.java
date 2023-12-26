/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import com.etiennecollin.ift2255.clientCLI.Client;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class JavaSerializedDatabase implements Database {
    public final String savePath;

    public JavaSerializedDatabase() {
        try {
            // Inspired by https://stackoverflow.com/a/3627527
            savePath = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile() + "/";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

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

    public <T extends DatabaseObject> List<T> get(DataMap dataMap, Predicate<T> filter) {
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            return data.stream().filter(filter).toList();
        }

        return new ArrayList<>();
    }

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

    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, UUID id) {
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter((entry) -> entry.getId() == id).toList();
            if (filteredData.size() > 0) {
                update.accept(filteredData.get(0));
            }
            save(data, path);
            return true;
        }

        return false;
    }

    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, Predicate<T> filter) {
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter(filter).toList();
            filteredData.forEach(update);
            save(data, path);
            return true;
        }

        return false;
    }

    public <T extends DatabaseObject> boolean remove(DataMap dataMap, UUID id) {
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter((v) -> v.getId() != id).toList();
            save(filteredData, path);
            return true;
        }

        return false;
    }

    public <T extends DatabaseObject> boolean remove(DataMap dataMap, Predicate<T> filter) {
        String path = dataMap.getFilename();

        List<T> data = load(path);
        if (data != null) {
            List<T> filteredData = data.stream().filter((v) -> !filter.test(v)).toList();
            save(filteredData, path);
            return true;
        }

        return false;
    }

    /**
     * Saves the current state of some data to the specified file path.
     *
     * @param data     Some data to store to the file.
     * @param filename The file name to which data needs to be saved.
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
