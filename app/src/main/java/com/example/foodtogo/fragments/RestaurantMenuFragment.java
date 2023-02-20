package com.example.foodtogo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtogo.R;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.model.Cart;
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

import com.example.foodtogo.adapters.MenuListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantMenuFragment extends Fragment implements MenuListAdapter.MenuListClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private MenuListAdapter menuListAdapter;
    private List<Menu> menuList = null;
    private TextView buttonCheckout;
    private List<Menu> itemsInCartList;
    private int totalItemInCart = 0;
    DBHelper DB;

    public RestaurantMenuFragment(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public RestaurantMenuFragment() {
    }

    public static RestaurantMenuFragment newInstance(String param1, String param2) {
        RestaurantMenuFragment fragment = new RestaurantMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //        buttonCheckout = myView.findViewById(R.id.buttonCheckout);
//        buttonCheckout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
//                    Toast.makeText(getActivity(), "Please add some items in cart.", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if(itemsInCartList == null && itemsInCartList.size() <= 0) {
//                    Toast.makeText(getActivity(), "Please add some items in cart.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                CartFragment restaurantMenuFragment = new CartFragment(itemsInCartList);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout,restaurantMenuFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        List<RestaurantModel> restaurantModelList =  getRestaurantData();

//        buttonCheckout = view.findViewById(R.id.buttonCheckout);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    private List<RestaurantModel> getRestaurantData() {
        InputStream is = getResources().openRawResource(R.raw.restaurent);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        RestaurantModel[] restaurantModels =  gson.fromJson(jsonStr, RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);

        return  restList;

    }



    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onAddToCartClick(Menu menu) {
        if(itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(menu);
        totalItemInCart = 0;

        for(Menu m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }

//        buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;

            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
//            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);
            totalItemInCart = 0;

            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
//            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }
}