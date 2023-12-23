/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {
    private final UUID id;
    private final ArrayList<Ticket> tickets;
    private final ArrayList<Notification> notifications;
    private String email;
    private String phoneNumber;
    private String address;
    private int password;

    public User(String email, String password, String phoneNumber, String address) {
        this.email = email;
        this.password = password.hashCode();
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.id = UUID.randomUUID();
        this.tickets = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        this.notifications.remove(notification);
    }

    public void updatePassword(String oldPassword, String newPassword) {
        if (oldPassword.hashCode() != this.getPassword()) {
            throw new IllegalArgumentException("Invalid match with current password");
        }

        this.setPassword(newPassword);
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.hashCode();
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public UUID getId() {
        return id;
    }
}
