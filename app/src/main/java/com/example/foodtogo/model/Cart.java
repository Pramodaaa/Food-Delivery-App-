package com.example.foodtogo.model;

public class Cart {
    int image;
    String name;
    String desc;
    String rest;
    String count;
    double price;

    public Cart(int image, String name, String desc, String rest, String count, double price) {
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.rest = rest;
        this.count = count;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
