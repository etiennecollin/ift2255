/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class DatabaseObject implements Serializable {
    private final UUID id;

    public DatabaseObject() {
        this.id = UUID.randomUUID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public UUID getId() {
        return id;
    }
}
