package com.example.foodtogo.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtogo.R;
import com.example.foodtogo.adapters.HomeListAdapter;
import com.example.foodtogo.adapters.MenuListAdapter;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.model.Menu;
import com.example.foodtogo.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private HomeListAdapter menuListAdapter;
    List<Menu> menuNewList;
    DBHelper DB;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(getContext());
        menuNewList =  getRestaurantData();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        menuNewList =  getRestaurantData();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         List<Menu> menuNewList =  getRestaurantData();
        recyclerView = view.findViewById(R.id.recyclerHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        menuListAdapter = new HomeListAdapter(menuNewList);
        recyclerView.setAdapter(menuListAdapter);
    }

    // returns a menu list
    private List<Menu> getRestaurantData() {

        List<Menu> menuList = new ArrayList<>();

        Cursor cursor = DB.readAllMenuData();
        if(cursor.getCount() == 0) {

        } else {

            // moves through and adds the stuff
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