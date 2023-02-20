package com.example.foodtogo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu {
    private String name;
    private String rest;
    private float price;
    private int totalInCart;
    private String desc;
    private int url;

    public Menu(String name, String rest, float price, int totalInCart, String desc, int url) {
        this.name = name;
        this.rest = rest;
        this.price = price;
        this.totalInCart = totalInCart;
        this.desc = desc;
        this.url = url;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public Menu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
