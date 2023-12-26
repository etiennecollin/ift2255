/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract class representing an object that can be stored in a database.
 */
public abstract class DatabaseObject implements Serializable {
    /**
     * The unique identifier of the object.
     */
    private final UUID id;

    /**
     * Constructs a DatabaseObject with a unique identifier.
     */
    public DatabaseObject() {
        this.id = UUID.randomUUID();
    }

    /**
     * Overrides the default hashCode method to use the identifier for hashing.
     *
     * @return The hash code based on the identifier.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Gets the unique identifier of the DatabaseObject.
     *
     * @return The UUID.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Overrides the default equals method to compare objects based on their identifiers.
     *
     * @param o The object to compare with.
     *
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseObject that = (DatabaseObject) o;
        return Objects.equals(getId(), that.getId());
    }
}
