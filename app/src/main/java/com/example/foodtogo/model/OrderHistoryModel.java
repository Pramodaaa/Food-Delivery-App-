package com.example.foodtogo.model;

// same as menu list
public class OrderHistoryModel {

    private String name;
    private String rest;
    private int count;
    private int img;
    private String user;
    private String dateTime;


    public OrderHistoryModel(String name, String rest, int count, int img, String user, String dateTime) {
        this.name = name;
        this.rest = rest;
        this.count = count;
        this.img = img;
        this.user = user;
        this.dateTime = dateTime;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public OrderHistoryModel() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
