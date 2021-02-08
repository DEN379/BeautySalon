package com.sakadel.salon.entity;

public enum Role {
    ADMIN("Admin"),
    MASTER("Master"),
    CLIENT("Client");

    private String value;

    public String value() {
        return value;
    }

    Role(String value) {
        this.value = value.intern();
    }
}
