package com.classy.class_2021a_and_b9;

public class User {

    private String uid = "";
    private String name = "";
    private String phone = "";
    private String favoriteProduct;

    public User() { }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFavoriteProduct() {
        return favoriteProduct;
    }

    public User setFavoriteProduct(String favoriteProduct) {
        this.favoriteProduct = favoriteProduct;
        return this;
    }
}
