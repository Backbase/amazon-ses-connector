package com.backbase.communication.model;

public enum EmailVersionEnum {
    V1("V1"),
    V2("V2");
    private final String value;

    EmailVersionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
