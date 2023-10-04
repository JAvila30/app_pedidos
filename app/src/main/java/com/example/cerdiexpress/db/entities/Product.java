package com.example.cerdiexpress.db.entities;

public class Product {

    private int id;
    private String name;

    public Product(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product(String name) {
        this.name = name;
    }

    public Product() {
        this.name = null;
        this.id = 0;
    }

    @Override
    public String toString() {
        return "product{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
