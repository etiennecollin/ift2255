/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.models.data.Notification;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The {@code UserNotifications} class represents a view for displaying and managing user notifications in the CLI application.
 * It allows users to view a list of notifications and perform actions such as deleting specific notifications.
 * <p>
 * The class extends the {@link View} class and interacts with the {@link ProfileController} for retrieving and processing notification data.
 * Notifications are displayed in a paginated manner, and users can choose to delete specific notifications.
 */
public class UserNotifications extends View {
    /**
     * The ProfileController used for interacting with buyer profiles and related actions.
     */
    private final ProfileController profileController;

    /**
     * Constructs a new {@code UserNotifications} instance with the specified profile controller.
     *
     * @param profileController The controller responsible for user profile-related actions.
     */
    public UserNotifications(ProfileController profileController) {
        this.profileController = profileController;
    }

    /**
     * Renders the user notifications view, allowing users to view and manage their notifications.
     * Users can delete specific notifications from the list.
     */
    @Override
    public void render() {
        clearConsole();
        List<Notification> notifications = profileController.getNotifications();
        if (notifications.isEmpty()) {
            System.out.println(prettify("No notifications"));
            waitForKey();
        } else {
            Consumer<Notification> notificationConsumer = (notification) -> {
                System.out.println(prettify("--------------------"));
                System.out.println(prettify(notification.getTitle()));
                System.out.println(prettify(notification.getContent()));
            };

            Function<Notification, Boolean> action = (notification) -> {
                profileController.removeNotification(notification.getId());
                return false;
            };
            prettyPaginationMenu(notifications, 3, "Delete notification", notificationConsumer, Notification::getTitle, action, null);
        }
    }
}
