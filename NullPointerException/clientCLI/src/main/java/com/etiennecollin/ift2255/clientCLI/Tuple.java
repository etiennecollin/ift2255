/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import java.io.Serializable;

/**
 * A simple serializable class representing a tuple of two elements.
 *
 * @param <A> The type of the first element.
 * @param <B> The type of the second element.
 */
public class Tuple<A, B> implements Serializable {
    /**
     * The first element of the tuple.
     */
    public A first;
    /**
     * The second element of the tuple.
     */
    public B second;

    /**
     * Constructs a new Tuple with the specified first and second elements.
     *
     * @param first  The first element.
     * @param second The second element.
     */
    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }
}