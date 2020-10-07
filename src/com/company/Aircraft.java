package com.company;

public class Aircraft {
    private String name, model;
    private static final int capacity = 100;

    public Aircraft(String name, String model) {
        this.name = name;
        this.model = model;
    }


    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
