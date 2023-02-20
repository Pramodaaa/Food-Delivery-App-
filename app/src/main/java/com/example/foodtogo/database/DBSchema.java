package com.example.foodtogo.database;

public class DBSchema
{


    public static class UserTable
    {
        public static final String NAME = "users";
        public static class Columns
        {
            public static final String EMAIL = "email";
            public static final String PASS = "password";
        }
    }


    public static class OrderHistoryTable
    {
        public static final String NAME = "order_history";
        public static class Columns
        {
            public static final String USER = "user";
            public static final String REST = RestaurantTable.Columns.REST;
            public static final String IMG = "image";
            public static final String DATETIME = "datetime";
            public static final String ITEM = MenuTable.Columns.ITEM;
            public static final String QTY = "quantity";
        }
    }


    public static class MenuTable
    {
        public static final String NAME = "menu";
        public static class Columns
        {
            public static final String ITEM = "item";
            public static final String REST = RestaurantTable.Columns.REST;
            public static final String IMG = "image";
            public static final String DESC = "description";
            public static final String PRICE = "price";
        }
    }

    public static class CartTable
    {
        public static final String NAME = "cart";
        public static class Columns
        {
            public static final String ITEM = "item";
            public static final String IMG = "image";
            public static final String REST = RestaurantTable.Columns.REST;
            public static final String DESC = "description";
            public static final String PRICE = "price";
            public static final String COUNT = "count";
        }
    }


    public static class RestaurantTable
    {
        public static final String NAME = "restaurants";
        public static class Columns
        {
            public static final String REST = "restaurant";
            public static final String LOGO = "logo_image";
        }
    }

}