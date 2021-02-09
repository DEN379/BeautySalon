package com.sakadel.salon.entity;

public enum  Status {
    PENDING("pending"),
    PAID("paid"),
    ACCEPTED("accepted"),
    FINISHED("finished");

    private String value;

    public String value() {
        return value;
    }

    Status(String value) {
        this.value = value.intern();
    }
}
