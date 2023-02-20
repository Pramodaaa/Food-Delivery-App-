package com.example.foodtogo.model;

import java.util.List;

public class RestaurantModel {

    private String name;
    private String address;
    private int image;
    private List<Menu> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public RestaurantModel() {

    }

    public RestaurantModel(String name, String address, int image, List<Menu> menus) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.menus = menus;
    }
}
