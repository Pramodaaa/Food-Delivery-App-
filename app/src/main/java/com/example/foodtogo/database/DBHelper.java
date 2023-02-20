package com.example.foodtogo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.foodtogo.R;
import com.example.foodtogo.database.DBSchema.*;
import com.example.foodtogo.model.Dish;
import com.example.foodtogo.model.Menu;
import com.example.foodtogo.model.User;;import java.time.LocalDateTime;


public class DBHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;               //Database version
    private static final String DB_NAME = "Food.db";    //Database name
    private Context context;


    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        /* Enables foreign key constraints */
        db.setForeignKeyConstraintsEnabled(false);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /* Creates the database schema */
        createTables(db);

        /* Populates restaurant tables with default values */
        fillRestaurantTable(db);
        fillMenuTable(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS" + );
        onCreate(db);
    }


    public void createTables(SQLiteDatabase db)
    {
        /* Creates User Table */
        db.execSQL("CREATE TABLE " + UserTable.NAME + " (" +
                UserTable.Columns.EMAIL + " TEXT PRIMARY KEY, " +
                UserTable.Columns.PASS + " TEXT) "
        );

        /* Creates Restaurant Table */
        db.execSQL("CREATE TABLE " + RestaurantTable.NAME + " (" +
                RestaurantTable.Columns.REST + " TEXT PRIMARY KEY, " +
                RestaurantTable.Columns.LOGO + " INTEGER) "
        );

        /* Creates Menu Table */
        db.execSQL("CREATE TABLE " + MenuTable.NAME + " (" +
                MenuTable.Columns.ITEM + " TEXT, " +
                MenuTable.Columns.REST + " TEXT, " +
                MenuTable.Columns.IMG + " INTEGER, " +
                MenuTable.Columns.DESC + " TEXT, " +
                MenuTable.Columns.PRICE + " REAL, " +
                "PRIMARY KEY (" +
                MenuTable.Columns.ITEM + ", " +
                MenuTable.Columns.REST + "), " +
                "FOREIGN KEY (" + MenuTable.Columns.REST +
                ") REFERENCES " + RestaurantTable.NAME +
                " (" + RestaurantTable.Columns.REST +
                "))"
        );

        /* Creates Order History Table */
        db.execSQL("CREATE TABLE " + OrderHistoryTable.NAME + " (" +
                        OrderHistoryTable.Columns.USER + " TEXT, " +
                        OrderHistoryTable.Columns.DATETIME + " TEXT, " +
                        OrderHistoryTable.Columns.REST + " TEXT, " +
                        OrderHistoryTable.Columns.ITEM + " TEXT, " +
                        OrderHistoryTable.Columns.QTY + " INTEGER, " +
                        OrderHistoryTable.Columns.IMG + " INTEGER, " +
                        "PRIMARY KEY (" +
                        OrderHistoryTable.Columns.USER + ", " +
                        OrderHistoryTable.Columns.DATETIME + ", " +
                        OrderHistoryTable.Columns.ITEM + "), " +
                        "FOREIGN KEY (" + OrderHistoryTable.Columns.USER +
                        ") REFERENCES " + UserTable.NAME +
                        " (" + UserTable.Columns.EMAIL +
                        "))"
                /*"FOREIGN KEY (" + OrderHistoryTable.Columns.ITEM +
                ") REFERENCES " + MenuTable.NAME +
                " (" + MenuTable.Columns.ITEM +
                "))"*/ /* This doesn't work. Can't figure out why */
        );

        db.execSQL("CREATE TABLE " + CartTable.NAME + " (" +
                CartTable.Columns.ITEM + " TEXT, " +
                CartTable.Columns.REST + " TEXT, " +
                CartTable.Columns.IMG + " INTEGER, " +
                CartTable.Columns.COUNT + " TEXT, " +
                CartTable.Columns.DESC + " TEXT, " +
                CartTable.Columns.PRICE + " REAL, " +
                "PRIMARY KEY (" +
                CartTable.Columns.ITEM + ", " +
                CartTable.Columns.REST + "), " +
                "FOREIGN KEY (" + CartTable.Columns.REST +
                ") REFERENCES " + RestaurantTable.NAME +
                " (" + RestaurantTable.Columns.REST +
                "))"
        );

    }


    public void fillRestaurantTable(SQLiteDatabase db)
    {
        /* Restaurant names */
        final String[] restaurantNames = {
                "Dinemore",
                "Subway",
                "Pizza Hut",
                "Domino's",
                "KFC",
                "Popeyes",
                "Hungry Jack's",
                "McDonald's",
                "Burger king",
                "Taco Bell"
        };

        /* Integer values representing restaurant logos - must be in the same
            order as the restaurant names */
        final int[] restaurantImgs = {
                R.drawable.dinemore_logo,
                R.drawable.subway_logo,
                R.drawable.pizzahut_logo,
                R.drawable.dominos_logo,
                R.drawable.kfc_logo,
                R.drawable.popeyes_logo,
                R.drawable.hungryjacks_logo,
                R.drawable.mcdonalds_logo,
                R.drawable.burgerking_logo,
                R.drawable.tacobell_logo
        };

        ContentValues cv = new ContentValues();

        /* Iterates through the arrays, constructs each tuple and adds them to
            the database */
        for (int i = 0; i < restaurantImgs.length; i++)
        {
            cv.put(RestaurantTable.Columns.REST, restaurantNames[i]);
            cv.put(RestaurantTable.Columns.LOGO, restaurantImgs[i]);

            db.insertOrThrow(RestaurantTable.NAME, null, cv);
            cv.clear();
        }
    }

    /* Fill the menu table of relative restaurant */
    public void fillMenuTable(SQLiteDatabase db)
    {
        final String[] restaurantNames = {
                "Dinemore",
                "Subway",
                "Pizza Hut",
                "Domino's",
                "KFC",
                "Popeyes",
                "Hungry Jack's",
                "McDonald's",
                "Burger king",
                "Taco Bell"
        };

        /* Dinemore  */
        addDishes(db, restaurantNames[0], new Dish[]{
                new Dish(R.drawable.americansandwich_dinemore, "Bacon & Cheese Sub", "A classic american sub", 800),
                new Dish(R.drawable.beefsub_dinemore, "Beef & Cheese Sub", "Spicy beef and chessy sub with fries ", 1100),
                new Dish(R.drawable.beefsub_subway, "Classic Submarine", "A simple classic sub", 800),
                new Dish(R.drawable.chickensub_dinemore, "Chicken Sub", "Our famous chicken sub with fries", 1100),
                new Dish(R.drawable.classicsub_subway, "Dinomore Sub", "Sub with tomato and chicken with mayo", 1000),
                new Dish(R.drawable.clubsub_dinemore, "Club sandwich", "Our famous chicken club sandwich", 1200),
                new Dish(R.drawable.clubsandwich_subway, "All-time Fav sandwich", "Mightiest club sandwich", 1500),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double decker Sub", "Double chicken Sub with cheesy mayo on the side ", 1300),
                new Dish(R.drawable.hashsandwitch_subway, "Toast Sub", "Hash brown toasted sandwich ", 1000),
                new Dish(R.drawable.ham_dinemore, "Ham & cheese All Mighty ", "Ham and cheese with tomato ", 1100),
                new Dish(R.drawable.turkeysub_subway, "Turkey Sub", "All special turkey sandwich ", 1500),
                new Dish(R.drawable.vegsub_dinemore, "Veg Sub", "Vegetarian sub with mushrooms and tofu ", 1100),
                new Dish(R.drawable.whitesub_dinemore, "White All Boom Sub", "White sub with chicken and BOOM ", 1500),
                new Dish(R.drawable.beef_burger, "Classic burger", "Classic burger ", 1100),
                new Dish(R.drawable.creamyspagehetti_pizzahut, "Creamy spaghetti", "Creamy shrimp spaghetti", 1200),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.lavacake_pizzahut, "Lava-cake", "Lavacake dessert ", 900),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500),
                new Dish(R.drawable.beverages, "Beverage drinks", "Fruit Beverages (L)", 700),
                new Dish(R.drawable.sprite_bottle, "Sprite drink", "Sprite 500 ml bottle ", 500)
        });

        /* Subway */
        addDishes(db, restaurantNames[1], new Dish[]{
                new Dish(R.drawable.chickensub_dinemore_subway, "Double decker Sub", "Double chicken Sub with cheesy mayo on the side ", 1300),
                new Dish(R.drawable.americansandwich_dinemore, "Bacon & Cheese Sub", "A classic american sub", 800),
                new Dish(R.drawable.beefsub_dinemore, "Beef & Cheese Sub", "Spicy beef and chessy sub with fries ", 1100),
                new Dish(R.drawable.beefsub_subway, "Classic Submarine", "A simple classic sub", 800),
                new Dish(R.drawable.chickensub_dinemore, "Chicken Sub", "Our famous chicken sub with fries", 1100),
                new Dish(R.drawable.classicsub_subway, "Subby Sub", "Sub with tomato and chicken with mayo", 1000),
                new Dish(R.drawable.clubsub_dinemore, "Club sandwich", "Our famous chicken club sandwich", 1200),
                new Dish(R.drawable.clubsandwich_subway, "All-time Fav sandwich", "Mightiest club sandwich", 1500),
                new Dish(R.drawable.hashsandwitch_subway, "Toast Sub", "Hash brown toasted sandwich ", 1000),
                new Dish(R.drawable.ham_dinemore, "Ham & cheese All Mighty ", "Ham and cheese with tomato ", 1100),
                new Dish(R.drawable.turkeysub_subway, "Turkey Sub", "All special turkey sandwich ", 1500),
                new Dish(R.drawable.vegsub_dinemore, "Veg Sub", "Vegetarian sub with mushrooms and tofu ", 1100),
                new Dish(R.drawable.whitesub_dinemore, "White All Boom Sub", "White sub with chicken and BOOM ", 1500),
                new Dish(R.drawable.beef_burger, "Classic burger", "Classic burger ", 1100),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.lavacake_pizzahut, "Lava-cake", "Lavacake dessert ", 900),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500),
                new Dish(R.drawable.beverages, "Beverage drinks", "Fruit Beverages (L)", 700),
                new Dish(R.drawable.sprite_bottle, "Sprite drink", "Sprite 500 ml bottle ", 500)
        });

        /* Pizza Hut */
        addDishes(db, restaurantNames[2], new Dish[]{
                new Dish(R.drawable.cheesy_pizza, "Cheese lovers", "7 types of cheese on the pizza bun", 1500),
                new Dish(R.drawable.pepperonichessy_pizza, "Pepperoni & cheese pizza", "Our famous pepperoni pizza", 1600),
                new Dish(R.drawable.pineapple_pizza, "Hawaii pizza", "Chicken with cheesy pineapple", 1500),
                new Dish(R.drawable.mushrooms_pizza, "Trinity Veg Feast", "Our famous 5 ingredient veg pizza", 1700),
                new Dish(R.drawable.mixed_pizza, "Bacon feast", "Bacon and cheesy ham pizza", 1900),
                new Dish(R.drawable.chickenfeast_pizza, "Meat feast", "Chicken, bacon, beef and sausage pizza", 1000),
                new Dish(R.drawable.sausage_pizzahut, "Sausage pizza", "Classic sausage pizza", 1200),
                new Dish(R.drawable.beef_burger, "Classic burger", "Classic burger ", 1100),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.meatballspaghetti_pizzahut, "MeatBall spaghetti", "Classic spaghetti", 1400),
                new Dish(R.drawable.spicywings_dominos, "Chicken Wings (6PC)", "Spicy bbq chicken wings", 700),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.lavacake_pizzahut, "Lava-cake", "Lavacake dessert ", 900),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500),
                new Dish(R.drawable.beverages, "Beverage drinks", "Fruit Beverages (L)", 700),
                new Dish(R.drawable.sprite_bottle, "Sprite drink", "Sprite 500 ml bottle ", 500)
        });

        /* Domino's */
        addDishes(db, restaurantNames[3], new Dish[]{
                new Dish(R.drawable.beef_burger, "Classic burger", "Classic burger ", 1100),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.meatballspaghetti_pizzahut, "MeatBall spaghetti", "Classic spaghetti", 1400),
                new Dish(R.drawable.spicywings_dominos, "Chicken Wings (6PC)", "Spicy bbq chicken wings", 700),
                new Dish(R.drawable.cheesy_pizza, "Cheese lovers", "7 types of cheese on the pizza bun", 1500),
                new Dish(R.drawable.pepperonichessy_pizza, "Pepperoni & cheese pizza", "Our famous pepperoni pizza", 1600),
                new Dish(R.drawable.pineapple_pizza, "Hawaii pizza", "Chicken with cheesy pineapple", 1500),
                new Dish(R.drawable.mushrooms_pizza, "Trinity Veg Feast", "Our famous 5 ingredient veg pizza", 1700),
                new Dish(R.drawable.mixed_pizza, "Bacon feast", "Bacon and cheesy ham pizza", 1900),
                new Dish(R.drawable.chickenfeast_pizza, "Meat feast", "Chicken, bacon, beef and sausage pizza", 1000),
                new Dish(R.drawable.sausage_pizzahut, "Sausage pizza", "Classic sausage pizza", 1200),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.lavacake_pizzahut, "Lava-cake", "Lavacake dessert ", 900),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500),
                new Dish(R.drawable.beverages, "Beverage drinks", "Fruit Beverages (L)", 700),
                new Dish(R.drawable.sprite_bottle, "Sprite drink", "Sprite 500 ml bottle ", 500)
        });

        /* KFC */
        addDishes(db, restaurantNames[4], new Dish[]{
                new Dish(R.drawable.chickenbucket6pc_popeyes, "Chicken bucket ", "6PC", 1500),
                new Dish(R.drawable.chickenbucket12pc_kfc, "Chicken bucket", "12PC", 2500),
                new Dish(R.drawable.snacker_kfc, "Snacker", "Class chicken snacker", 700),
                new Dish(R.drawable.grilledchicken_kfc, "Grilled chicken bucket", "12PC", 2700),
                new Dish(R.drawable.friedrice_dinemore_subway, "Biryani rice", "Chicken biryani rice", 500),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.tendies_dominos, "Chicken tenders", "Chicken tender 10PC", 1000),
                new Dish(R.drawable.pork_burger, "Classic burger", "A classic burger ", 1000),
                new Dish(R.drawable.cheesyfried_burger, "Fried cheese burger", "Cheesy mouth watering burger", 1600),
                new Dish(R.drawable.burrito_mac, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.spicywings_dominos, "Chicken wings", "6 Fried Chicken tender", 1000),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.friedchicken_burger, "Fried Chicken burger", "Hot & fried chicken burger", 1300),
                new Dish(R.drawable.familymeal_popeyes, "Family meal ", "Family fried chicken burger meal ", 3000),
                new Dish(R.drawable.lavacake_pizzahut, "Lava-cake", "Lavacake dessert ", 900),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500),
                new Dish(R.drawable.sprite_bottle, "Sprite drink", "Sprite 500 ml bottle ", 500)
        });

        /* Popeyes */
        addDishes(db, restaurantNames[5], new Dish[]{
                new Dish(R.drawable.friedrice_dinemore_subway, "Biryani rice", "Chicken biryani rice", 500),
                new Dish(R.drawable.chickenbucket6pc_popeyes, "Chicken bucket ", "6PC", 1500),
                new Dish(R.drawable.chickenbucket12pc_kfc, "Chicken bucket", "12PC", 2500),
                new Dish(R.drawable.snacker_kfc, "Snacker", "Class chicken snacker", 700),
                new Dish(R.drawable.grilledchicken_kfc, "Grilled chicken bucket", "12PC", 2700),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.tendies_dominos, "Chicken tenders", "Chicken tender 10PC", 1000),
                new Dish(R.drawable.pork_burger, "Classic burger", "A classic burger ", 1000),
                new Dish(R.drawable.cheesyfried_burger, "Fried cheese burger", "Cheesy mouth watering burger", 1600),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.spicywings_dominos, "Chicken wings", "6 Fried Chicken tender", 1000),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.friedchicken_burger, "Fried Chicken burger", "Hot & fried chicken burger", 1300),
                new Dish(R.drawable.familymeal_popeyes, "Family Popeyes meal ", "Family fried chicken burger meal ", 3000),
                new Dish(R.drawable.lavacake_pizzahut, "Lava-cake", "Lavacake dessert ", 900),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500),
                new Dish(R.drawable.sprite_bottle, "Sprite drink", "Sprite 500 ml bottle ", 500)
        });

        /* Hungry Jack's */
        addDishes(db, restaurantNames[6], new Dish[]{
                new Dish(R.drawable.pork_burger, "Classic burger", "A classic burger ", 1000),
                new Dish(R.drawable.cheesyfried_burger, "Fried cheese burger", "Cheesy mouth watering burger", 1600),
                new Dish(R.drawable.blackburger, "Black chicken Meal", "Black chicken burger with fries", 1800),
                new Dish(R.drawable.doubleburger_kfc, "Double decker burger", "Chicken double decker burger", 1700),
                new Dish(R.drawable.friedchicken_burger, "Fried Chicken burger", "Hot & fried chicken burger", 1300),
                new Dish(R.drawable.fish_burger, "Big fish burger", "Classic fried fish burger with coke bottle", 2400),
                new Dish(R.drawable.ham_burger, "All special burger", "Special burger ", 1600),
                new Dish(R.drawable.macmeal_mac, "Chicken Burger Meal", "Burger with fries", 1600),
                new Dish(R.drawable.vegburger, "Classic Veg Burger", "A veg burger ", 1100),
                new Dish(R.drawable.cheesybeef_burger, "Salad beef burger", "Beef burger with salad", 1800),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.tendies_dominos, "Chicken tenders", "Chicken tender 10PC", 1000),
                new Dish(R.drawable.beefburrito_subway, "Beef Burrito", "Pulled-out beef burrito", 1000),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.meatballspaghetti_pizzahut, "MeatBall spaghetti", "Classic spaghetti", 1400),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500)
        });

        /*  McDonald's */
        addDishes(db, restaurantNames[7], new Dish[]{
                new Dish(R.drawable.ham_burger, "All special burger", "Special burger ", 1600),
                new Dish(R.drawable.cheesyfried_burger, "Fried cheese burger", "Cheesy mouth watering burger", 1600),
                new Dish(R.drawable.beef_burger, "Classic burger", "Classic burger ", 1100),
                new Dish(R.drawable.blackburger, "Black chicken Meal", "Black chicken burger with fries", 1800),
                new Dish(R.drawable.doubleburger_kfc, "Double decker burger", "Chicken double decker burger", 1700),
                new Dish(R.drawable.friedchicken_burger, "Fried Chicken burger", "Hot & fried chicken burger", 1300),
                new Dish(R.drawable.fish_burger, "Big fish burger", "Classic fried fish burger with coke bottle", 2400),
                new Dish(R.drawable.macmeal_mac, "Chicken Burger Meal", "Burger with fries", 1600),
                new Dish(R.drawable.vegburger, "Classic Veg Burger", "A veg burger ", 1100),
                new Dish(R.drawable.cheesybeef_burger, "Salad beef burger", "Beef burger with salad", 1800),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.tendies_dominos, "Chicken tenders", "Chicken tender 10PC", 1000),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.meatballspaghetti_pizzahut, "MeatBall spaghetti", "Classic spaghetti", 1400),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500)
        });

        /* burger king */
        addDishes(db, restaurantNames[8], new Dish[]{
                new Dish(R.drawable.creamyspagehetti_pizzahut, "Creamy spaghetti", "Creamy shrimp spaghetti", 1200),
                new Dish(R.drawable.cheesyfried_burger, "Fried cheese burger", "Cheesy mouth watering burger", 1600),
                new Dish(R.drawable.beef_burger, "Classic burger", "Classic burger ", 1100),
                new Dish(R.drawable.blackburger, "Black chicken Meal", "Black chicken burger with fries", 1800),
                new Dish(R.drawable.doubleburger_kfc, "Double decker burger", "Chicken double decker burger", 1700),
                new Dish(R.drawable.friedchicken_burger, "Fried Chicken burger", "Hot & fried chicken burger", 1300),
                new Dish(R.drawable.fish_burger, "Big fish burger", "Classic fried fish burger with coke bottle", 2400),
                new Dish(R.drawable.ham_burger, "All special burger", "Special burger ", 1600),
                new Dish(R.drawable.macmeal_mac, "Chicken Burger Meal", "Burger with fries", 1600),
                new Dish(R.drawable.vegburger, "Classic Veg Burger", "A veg burger ", 1100),
                new Dish(R.drawable.cheesybeef_burger, "Salad beef burger", "Beef burger with salad", 1800),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.tendies_dominos, "Chicken tenders", "Chicken tender 10PC", 1000),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.meatballspaghetti_pizzahut, "MeatBall spaghetti", "Classic spaghetti", 1400),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500)
        });

        /* taco bell */
        addDishes(db, restaurantNames[9], new Dish[]{
                new Dish(R.drawable.crunchy_subway, "Soft crunchy taco", "Beef and salad taco with mayo", 1000),
                new Dish(R.drawable.vegtaco_tacobell, "Veg taco", "Veg taco", 1200),
                new Dish(R.drawable.chulga_tacobell, "Chulga Taco", "Taco with minced beef, chicken and mayo", 1500),
                new Dish(R.drawable.cheesytaco_kfc, "Cheesy Tortilla", "Pulled pork, lettuce, rice, beans and cheese in tortilla", 1000),
                new Dish(R.drawable.beeftacos_tacobell, "Beef taco", "Minced beef taco", 1300),
                new Dish(R.drawable.wraps_tacobel, "Cheesy wrap taco Meal", "Wrapped taco with fries and coke", 2300),
                new Dish(R.drawable.thicktaco_tacobell, "Thick chicken taco ", "Chicken wrapped thick tortilla taco", 1000),
                new Dish(R.drawable.quesadilla_tacobell, "Quesadilla", "Grilled chicken, lettuce, corn, rice and beans in tortilla", 1200),
                new Dish(R.drawable.nachos_tacobell, "Nachos with guacamole", "Guacamole and sour cream with corn chips ", 1400),
                new Dish(R.drawable.pork_burger, "Classic burger", "A classic burger ", 1000),
                new Dish(R.drawable.cheesyfried_burger, "Fried cheese burger", "Cheesy mouth watering burger", 1600),
                new Dish(R.drawable.burrito_burgerking, "Famous burrito", "Classic burrito", 1400),
                new Dish(R.drawable.chickensub_dinemore_subway, "Double Chicken Sub", "Double chicken Sub", 1300),
                new Dish(R.drawable.beefburrito_subway, "Beef burrito", "Beef burrito", 1200),
                new Dish(R.drawable.burrito_kfc, "Veg burrito", "Veg burrito ", 1200),
                new Dish(R.drawable.fries_tacobell, "Animal-style fries", "Fries with bbq, cheese or with any sauce preference", 900),
                new Dish(R.drawable.frenchfries_burgerking, "French fries (L)", "A large fries", 500),
                new Dish(R.drawable.nuggets_mac, "Chicken nuggets (6PC)", "Nuggets", 500),
                new Dish(R.drawable.tendies_dominos, "Chicken tenders", "Chicken tender 10PC", 1000),
                new Dish(R.drawable.brownie_burgerking, "Fudge Brownie ", "Chocolate Brownie ", 900),
                new Dish(R.drawable.coke_bottle, "Coca cola drink", "Coca cola 500 ml bottle ", 500)
        });
    }

    public void addDishes(SQLiteDatabase db, String restaurantName, Dish[] dishes)
    {
        ContentValues cv = new ContentValues();

        /* Iterates through the array of dishes, constructs each tuple and adds
            them to the database */
        for(int i = 0; i < dishes.length; i++)
        {
            cv.put(MenuTable.Columns.ITEM, dishes[i].getName());
            cv.put(MenuTable.Columns.REST, restaurantName);
            cv.put(MenuTable.Columns.IMG, dishes[i].getImage());
            cv.put(MenuTable.Columns.DESC, dishes[i].getDesc());
            cv.put(MenuTable.Columns.PRICE, dishes[i].getPrice());

            /* Throws an exception if there is an SQL error - not using
                try-catch as these values are "hard-coded" and therefore an
                exception should not be thrown unless there is a mistake in
                programming */
            db.insertOrThrow(MenuTable.NAME, null, cv);
            cv.clear();
        }
    }

    public Boolean addUser(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(UserTable.Columns.EMAIL, username);
        contentValues.put(UserTable.Columns.PASS, password);
        long result = MyDB.insert(UserTable.NAME, null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public void addToCart(String item,String desc,int img,float price,int count,String rest) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartTable.Columns.ITEM, item);
        values.put(CartTable.Columns.REST, rest);
        values.put(CartTable.Columns.DESC, desc);
        values.put(CartTable.Columns.IMG, img);
        values.put(CartTable.Columns.PRICE, price);
        values.put(CartTable.Columns.COUNT, count);
        db.insert(CartTable.NAME, null, values);
        db.close();
    }

    public void updateCart(Menu menu, int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartTable.Columns.ITEM, menu.getName());
        values.put(CartTable.Columns.DESC, menu.getDesc());
        values.put(CartTable.Columns.IMG, menu.getUrl());
        values.put(CartTable.Columns.PRICE, menu.getPrice());
        values.put(CartTable.Columns.COUNT, count);
        db.update(CartTable.NAME, values,"item = ?",new String[] {menu.getName()});
        db.close();
    }

    public void deleteCartItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CartTable.NAME,"item = ?",new String[] {item});
    }

    public void deleteCartData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + CartTable.NAME);
    }

    public void addToOrderHistory(String item,int count,String user,int img) {
        SQLiteDatabase db = this.getWritableDatabase();
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        ContentValues values = new ContentValues();
        values.put(OrderHistoryTable.Columns.ITEM, item);
        values.put(OrderHistoryTable.Columns.DATETIME, String.valueOf(now));
        values.put(OrderHistoryTable.Columns.USER, user);
        values.put(OrderHistoryTable.Columns.QTY, count);
        values.put(OrderHistoryTable.Columns.IMG, img);
        db.insert(OrderHistoryTable.NAME, null, values);
        db.close();
    }


    public Cursor readOrderHistoryData(String user) {
        String qry = "SELECT * FROM order_history " + " WHERE " + OrderHistoryTable.Columns.USER + " = '" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(qry,null);
        }
        return cursor;
    }

    public Cursor readCartData() {
        String qry = "SELECT * FROM " + CartTable.NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(qry,null);
        }
        return cursor;
    }

    public Cursor readRestaurantData() {
        String qry = "SELECT * FROM " + RestaurantTable.NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(qry,null);
        }
        return cursor;
    }

    public Cursor readMenuData(String restaurantName) {
        String qry = "SELECT * FROM " + MenuTable.NAME + " WHERE " + MenuTable.Columns.REST + " = '" + restaurantName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(qry,null);
        }
        return cursor;
    }

    public Cursor readAllMenuData() {
        String qry = "SELECT * FROM " + MenuTable.NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(qry,null);
        }
        return cursor;
    }

}

