package com.example.cerdiexpress.db.entities;

public class Request {

    private int id;
    private String nombre;
    private int cantidad;
    private String contacto;
    private String producto;
    private String ordenante;

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

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", contacto='" + contacto + '\'' +
                ", producto='" + producto + '\'' +
                ", ordenante='" + ordenante + '\'' +
                '}';
    }

    public Request(int id, String nombre, int cantidad, String contacto, String producto, String ordenante) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.contacto = contacto;
        this.producto = producto;
        this.ordenante = ordenante;
    }

    public Request() {
        this.id = 0;
        this.nombre = null;
        this.cantidad = 0;
        this.contacto = null;
        this.producto = null;
        this.ordenante = null;
    }
}
