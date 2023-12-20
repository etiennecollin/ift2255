/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

public class Seller extends User {
    private String name; // Unique
    private String extraParam;

    public Seller(String name, int passwordHash, String email, String phoneNumber, String address, String ex) {
        super(email, passwordHash, phoneNumber, address);
        setName(name);
        setExtraParam(ex);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }
}
