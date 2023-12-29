/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SessionCartDatabase implements Database {
    List<CartProduct> cartProducts;

    public SessionCartDatabase() {
        cartProducts = new ArrayList<>();
    }

    @Override
    public <T extends DatabaseObject> T get(DataMap dataMap, UUID id) {
        List<CartProduct> filteredCart = cartProducts.stream().filter((entry) -> entry.getId().equals(id)).toList();
        if (filteredCart.size() == 0) {
            return null;
        }
        else {
            return (T) filteredCart.get(0);
        }
    }

    @Override
    public <T extends DatabaseObject> List<T> get(DataMap dataMap, Predicate<T> filter) {
        return ((List<T>) cartProducts).stream().filter(filter).toList();
    }

    @Override
    public <T extends DatabaseObject> boolean add(DataMap dataMap, T object) {
        cartProducts.add((CartProduct) object);
        return true;
    }

    @Override
    public <T extends DatabaseObject> boolean add(DataMap dataMap, List<T> objects) {
        cartProducts.addAll((List<? extends CartProduct>) objects);
        return true;
    }

    @Override
    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, UUID id) {
        List<CartProduct> filteredData = cartProducts.stream().filter((entry) -> entry.getId().equals(id)).toList();
        if (filteredData.size() > 0) {
            update.accept((T) filteredData.get(0));
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public <T extends DatabaseObject> boolean update(DataMap dataMap, Consumer<T> update, Predicate<T> filter) {
        List<T> filteredData = ((List<T>) cartProducts).stream().filter(filter).toList();
        filteredData.forEach(update);
        return true;
    }

    @Override
    public <T extends DatabaseObject> boolean remove(DataMap dataMap, UUID id) {
        cartProducts = (List<CartProduct>) ((List<T>) cartProducts).stream().filter((v) -> v.getId() != id).toList();
        return true;
    }

    @Override
    public <T extends DatabaseObject> boolean remove(DataMap dataMap, Predicate<T> filter) {
        cartProducts = (List<CartProduct>) ((List<T>) cartProducts).stream().filter((v) -> !filter.test(v)).toList();
        return true;
    }
}
