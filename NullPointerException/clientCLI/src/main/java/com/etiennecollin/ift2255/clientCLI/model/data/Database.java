/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Database {

    <T extends DatabaseObject> T get(DataMap dataMap, UUID id);

    <T extends DatabaseObject> List<T> get(DataMap dataMap, Predicate<T> filter);

    <T extends DatabaseObject> boolean add(DataMap dataMap, T object);

    <T extends DatabaseObject> boolean add(DataMap dataMap, List<T> objects);

    <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, UUID id);

    <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, Predicate<T> filter);

    <T extends DatabaseObject> boolean remove(DataMap dataMap, UUID id);

    <T extends DatabaseObject> boolean remove(DataMap dataMap, Predicate<T> filter);


}
