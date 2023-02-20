package com.example.foodtogo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.foodtogo.database.DBSchema.*;
import com.example.foodtogo.model.Dish;
import com.example.foodtogo.model.Menu;
import com.example.foodtogo.model.RestaurantModel;
import com.example.foodtogo.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class DBModel
{
    SQLiteDatabase db;


    public void load(Context context){
        this.db = new DBHelper(context).getWritableDatabase();
    }


    public void close()
    {
        this.db.close();
    }


//    public void addUser(String email, String passwd) throws IllegalArgumentException
//    {
//
//        if(!findUser(email, passwd))
//        {
//            ContentValues cv = new ContentValues();
//
//            cv.put(UserTable.Columns.EMAIL, email);
//            cv.put(UserTable.Columns.PASS, hashPasswd(passwd));
//
//            db.insert(UserTable.NAME, null, cv);
//        } else throw new IllegalArgumentException("User already exists");
//    }

//    public boolean findUser(String email, String passwd)
//    {
//        boolean userExists;
//
//        try (Cursor cursor = new Cu(db.query(UserTable.NAME, null,
//                UserTable.Columns.EMAIL + " = ? AND " + UserTable.Columns.PASS + " = ?",
//                new String[]{email, hashPasswd(passwd)}, null, null, "1")) {
//        }) {
//            userExists = cursor.getCount() != 0;
//        }
//
//        return userExists;
//    }


//    public ArrayList<RestaurantModel> getRestaurantModels()
//    {
//        ArrayList<RestaurantModel> RestaurantModelList = new ArrayList<>();
//        String RestaurantModelName;
//
//        try (RestaurantCursor restCursor = new RestaurantCursor(
//                db.query(RestaurantTable.NAME, null, null, null, null, null, null))) {
//            restCursor.moveToFirst();
//            while (!restCursor.isAfterLast()) {
//                restaurantName = restCursor.getName();
//
//                restaurantList.add(new Restaurant(restaurantName, restCursor.getDrawable(), getMenu(restaurantName)));
//                restCursor.moveToNext();
//            }
//        }
//
//        return restaurantList;
//    }

//    public void addToOrderHistory(Basket basket, String user)
//    {
//        ContentValues cv = new ContentValues();
//        LocalDateTime now = LocalDateTime.now();
//        for (Object bdObj : basket.getMapBasket().values())
//        {
//            if(bdObj instanceof BasketDish) {
//                BasketDish bd = (BasketDish)bdObj;
//
//                cv.put(OrderHistoryTable.Columns.USER, user);
//                cv.put(OrderHistoryTable.Columns.DATETIME, now.toString());
//                cv.put(OrderHistoryTable.Columns.ITEM, bd.getDishName());
//                cv.put(OrderHistoryTable.Columns.QTY, bd.getCount());
//
//                db.insert(OrderHistoryTable.NAME, null, cv);
//                cv.clear();
//            } else throw new IllegalArgumentException();
//        }
//    }

//    public ArrayList<Order> getOrders(String user)
//    {
//        ArrayList<Order> orderList = new ArrayList<>();
//        String date, curDate = "";
//        Order curOrder = null;
//        Dish dish;
//
//        try (OrderHistoryCursor cursor = new OrderHistoryCursor(db.query(OrderHistoryTable.NAME, null,
//                OrderHistoryTable.Columns.USER + " = " + user, null, null, null,
//                OrderHistoryTable.Columns.DATETIME + " ASC"))) {
//            cursor.moveToFirst();
//
//            while (!cursor.isAfterLast()) {//here  while (!cursor.isAfterLast())
//                date = cursor.getDateTime();
//                /* If the date being read is different to the current date
//                    value stored, pop the current order onto the list and start
//                    a new one */
//                if (!curDate.equals(date)) {
//                    if (!curDate.equals("")) {
//                        orderList.add(curOrder);
//                    }
//
//                    curDate = date;
//                    curOrder = new Order(curDate, user, findRestaurant(cursor.getDishName()));
//                }
//
//                dish = findDish(cursor.getDishName());
//                curOrder.addDish(dish, cursor.getQty());
//
//                cursor.moveToNext();
//            }
//
//            orderList.add(curOrder);
//        }
//
//        return orderList;
//    }

//    private String findRestaurant(String dishName)
//    {
//        String rest;
//        MenuCursor cursor = new MenuCursor(db.query(MenuTable.NAME, null,
//                MenuTable.Columns.ITEM + " = ?",
//                new String[]{dishName}, null, null, null));
//
//        try
//        {
//            cursor.moveToFirst();
//            rest = cursor.getRestaurant();
//        }
//        finally
//        {
//            cursor.close();
//        }
//
//        return rest;
//    }

//    private Dish findDish(String dishName)
//    {
//        Dish dish;
//        MenuCursor cursor = new MenuCursor(db.query(MenuTable.NAME, null,
//                MenuTable.Columns.ITEM + " = ?",
//                new String[]{dishName}, null, null, null));
//
//        try
//        {
//            cursor.moveToFirst();
//            dish = cursor.getDish();
//        }
//        finally
//        {
//            cursor.close();
//        }
//
//        return dish;
//    }


//    private Menu getMenu(String menuName)
//    {
//        MenuCursor menuCursor = new MenuCursor(db.query(MenuTable.NAME, null, MenuTable.Columns.REST + " = ?",
//                new String[]{menuName}, null, null, null));
//        Menu menu = new Menu();
//
//        try
//        {
//            menuCursor.moveToFirst();
//
//            while(!menuCursor.isAfterLast())
//            {
//                menu.addDish(menuCursor.getDish());
//                menuCursor.moveToNext();
//            }
//        }
//        finally
//        {
//            menuCursor.close();
//        }
//
//        return menu;
//    }

//    public void addUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(UserTable.Columns.EMAIL, user.getEmail());
//        values.put(UserTable.Columns.PASS, user.getPassword());
//        db.insert(UserTable.NAME, null, values);
//        db.close();
//    }


    private String hashPasswd(String passwd)
    {
        String hashedPasswd;
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            hashedPasswd = new String(md.digest(passwd.getBytes(StandardCharsets.UTF_8)));
        }
        catch (NoSuchAlgorithmException e) {hashedPasswd = null;}
        return hashedPasswd;
    }

//    void createCart(String cartId, int productId, int productQuantity) {
//        SQLiteDatabase sqlDB = getWritableDatabase();
//        String sql = "INSERT INTO productCartTable VALUES (NULL, ?, ?, ?)";
//        SQLiteStatement statement = sqlDB.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(1, cartId);
//        statement.bindDouble(2, productId);
//        statement.bindDouble(3, productQuantity);
//
//        statement.executeInsert();
//    }
}
