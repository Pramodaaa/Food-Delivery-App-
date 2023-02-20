package com.example.foodtogo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.fragments.CartFragment;
import com.example.foodtogo.fragments.HomeFragment;
import com.example.foodtogo.fragments.ProfileFragment;
import com.example.foodtogo.fragments.SearchFragment;
import com.example.foodtogo.model.Menu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CartFragment cartFragment = new CartFragment();
    SearchFragment searchFragment = new SearchFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    DBHelper DB;
    Globals sharedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new DBHelper(this);
        sharedData = Globals.getInstance();

        sharedData.setValue(false);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();
                    return true;

                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,searchFragment).commit();
                    return true;

                case R.id.cart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,cartFragment).commit();
                    return true;

                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profileFragment).commit();
                    return true;

            }
            return false;
        });

    }

    private List<Menu> getRestaurantData() {

        List<Menu> menuList = new ArrayList<>();

        Cursor cursor = DB.readAllMenuData();
        if(cursor.getCount() == 0) {

        } else {

            while (cursor.moveToNext()) {
                Menu menu = new Menu();
                int index;

                index = cursor.getColumnIndexOrThrow("item");
                menu.setName(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("image");
                menu.setUrl(cursor.getInt(index));
                index = cursor.getColumnIndexOrThrow("description");
                menu.setDesc(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("price");
                menu.setPrice(cursor.getFloat(index));
                menuList.add(menu);
            }


        }

        return  menuList;

    }
}