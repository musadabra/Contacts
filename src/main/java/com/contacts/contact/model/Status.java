package com.contacts.contact.model;

import java.util.Arrays;

/**
 *
 * @author Musa Dabra
 */
public enum Status {
    ACTIVE(1),
    INACTIVE(0);
    private final int id;

    private Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Status findById(int id) {
        return Arrays.stream(Status.values()).filter(s -> s.id == id).findAny().orElse(INACTIVE);
    }
}
