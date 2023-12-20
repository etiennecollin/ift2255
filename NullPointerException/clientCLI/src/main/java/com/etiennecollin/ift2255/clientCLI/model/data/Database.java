/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Database {

    <T> List<T> get(DataMap dataMap, Predicate<T> filter);

    <T> boolean add(DataMap dataMap, T object);

    <T> boolean add(DataMap dataMap, List<T> objects);

    <T> boolean update(DataMap dataMap, Consumer<T> update, Predicate<T> filter);

    <T> boolean remove(DataMap dataMap, Predicate<T> filter);
}
