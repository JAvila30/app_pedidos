package com.example.cerdiexpress.enums;

public enum HeaderRequestEnum {
    Contacto("Contacto", "Campo contacto"),
    Producto("Producto", "Campo producto"),
    Nombre("Nombre", "Campo nombre"),
    Cantidad("Cantidad", "Campo producto"),
    Ordenante("Producto", "Campo producto"),
    Estado("Estado", "campo que representa el estado");
    private String field;
    private String description;

    HeaderRequestEnum(String field, String description) {
        this.field = field;
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }
}
