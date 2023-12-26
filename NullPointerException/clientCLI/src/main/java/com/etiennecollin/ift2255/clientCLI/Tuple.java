/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import java.io.Serializable;

public class Tuple<A, B> implements Serializable {
    public A first;
    public B second;

    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }
}