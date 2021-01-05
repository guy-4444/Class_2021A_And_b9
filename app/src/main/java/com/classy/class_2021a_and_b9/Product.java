package com.classy.class_2021a_and_b9;

public class Product {

    private String key = "";
    private String name = "";
    private double price = 0.0;
    private boolean milky = false;
    private boolean gluten = false;

    public Product() { }

    public String getKey() {
        return key;
    }

    public Product setKey(String key) {
        this.key = key;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public boolean isMilky() {
        return milky;
    }

    public Product setMilky(boolean milky) {
        this.milky = milky;
        return this;
    }

    public boolean isGluten() {
        return gluten;
    }

    public Product setGluten(boolean gluten) {
        this.gluten = gluten;
        return this;
    }
}
