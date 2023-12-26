/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.util.UUID;

/**
 * Represents a notification, extending DatabaseObject.
 */
public class Notification extends DatabaseObject {
    /**
     * The ID of the user to whom the notification belongs.
     */
    private final UUID userId;
    /**
     * The title of the notification.
     */
    private final String title;
    /**
     * The content of the notification.
     */
    private final String content;

    /**
     * Constructs a Notification object with the specified parameters.
     *
     * @param userId  The ID of the user to whom the notification belongs.
     * @param title   The title of the notification.
     * @param content The content of the notification.
     */
    public Notification(UUID userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    /**
     * Gets the ID of the user to whom the notification belongs.
     *
     * @return The UUID representing the user ID.
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Gets the title of the notification.
     *
     * @return The String representing the notification title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the content of the notification.
     *
     * @return The String representing the notification content.
     */
    public String getContent() {
        return content;
    }
}
