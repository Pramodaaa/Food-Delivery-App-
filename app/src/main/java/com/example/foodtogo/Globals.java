package com.example.foodtogo;

import com.example.foodtogo.model.Menu;

import java.util.List;

public class Globals {


    private static Globals instance = new Globals();

    // Getter-Setters
    public static Globals getInstance() {
        return instance;
    }

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    private Boolean loggedInUser;
    private String user;
    private List<Menu> cartList;


    private Globals() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getValue() {
        return loggedInUser;
    }


    public void setValue(Boolean loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<Menu> getCartList() {
        return cartList;
    }

    public void setCartList(List<Menu> cartList) {
        this.cartList = cartList;
    }
}
