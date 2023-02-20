package com.example.foodtogo.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtogo.R;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.model.Menu;
import com.example.foodtogo.model.RestaurantModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.foodtogo.adapters.RestaurantListAdapter;


public class SearchFragment extends Fragment implements RestaurantListAdapter.RestaurantListClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    DBHelper DB;

    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        List<RestaurantModel> restaurantModelList =  getRestaurantData();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<RestaurantModel> restaurantModelList =  getRestaurantData();

        recyclerView = view.findViewById(R.id.recyclerSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RestaurantListAdapter listAdapter = new RestaurantListAdapter(restaurantModelList, this);
        recyclerView.setAdapter(listAdapter);
    }

    private List<RestaurantModel> getRestaurantData() {

        List<RestaurantModel> restList = new ArrayList<>(); // creates a new list.

        Cursor cursor = DB.readRestaurantData();
        if(cursor.getCount() == 0) {

        } else {

            while (cursor.moveToNext()) {
                RestaurantModel restaurantModel = new RestaurantModel();
                int index;

                index = cursor.getColumnIndexOrThrow("restaurant");
                restaurantModel.setName(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("logo_image");
                restaurantModel.setImage(cursor.getInt(index));
                restList.add(restaurantModel);
            }


        }

        return  restList;

    }

    // when an image is clicked its added to the cart list
    @Override
    public void onItemClick(RestaurantModel restaurantModel) {
        List<Menu> menuList = new ArrayList<>();


        Cursor cursor = DB.readMenuData(restaurantModel.getName());
        if(cursor.getCount() == 0) {
            return;
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
        RestaurantMenuFragment restaurantMenuFragment = new RestaurantMenuFragment(menuList);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout,restaurantMenuFragment)
                .addToBackStack(null)
                .commit();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}