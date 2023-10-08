package com.example.cerdiexpress.db.entities;

import lombok.NoArgsConstructor;

public class Request {

    private int id;
    private String nombre;
    private int cantidad;
    private String contacto;
    private String producto;
    private String ordenante;
    private String status;
    private String orderId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getOrdenante() {
        return ordenante;
    }

    public void setOrdenante(String ordenante) {
        this.ordenante = ordenante;
    }

    public Request() {
        this.id = 0;
        this.nombre = "";
        this.cantidad = 0;
        this.contacto = "";
        this.producto = "";
        this.ordenante = "";
        this.status = "";
        this.orderId = "";
    }

}
