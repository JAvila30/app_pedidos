package com.example.cerdiexpress.enums;

public enum StatusRequestEnum {
    PENDING("PENDING","Estado pendiente"),
    FINISHED("FINISH","Entregado o terminado");

    private String key;
    private String description;

    public String getKey() {
        return key;
    }
    public String getDescription() {
        return description;
    }
    StatusRequestEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }
}
