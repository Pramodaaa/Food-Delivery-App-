package com.example.foodtogo.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodtogo.Globals;
import com.example.foodtogo.R;
import com.example.foodtogo.adapters.CartListAdapter;
import com.example.foodtogo.adapters.RestaurantListAdapter;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.model.Menu;
import com.example.foodtogo.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Menu> itemsInCartList;
    private RecyclerView recyclerView;
    DBHelper DB;
    TextView checkOutBtn;
    Globals sharedData;

    public CartFragment(List<Menu> itemsInCartList) {
        this.itemsInCartList = itemsInCartList;
    }

    public CartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        itemsInCartList = new ArrayList<>();
        sharedData = Globals.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DB = new DBHelper(view.getContext());
        displayCartData();
        recyclerView = view.findViewById(R.id.recyclerCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        CartListAdapter listAdapter = new CartListAdapter(itemsInCartList);
        recyclerView.setAdapter(listAdapter);

        checkOutBtn = view.findViewById(R.id.textView);
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.deleteCartData();
/*                notifyDataSetChanged();*/
                sharedData.setCartList(itemsInCartList);
                SigninFragment signinFragment = new SigninFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,signinFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    // displays all the cart information by moving using the cursor
    void displayCartData() {

        Cursor cursor = DB.readCartData();
        if(cursor.getCount() == 0) {
            return;
        } else {

            while (cursor.moveToNext()) {
                Menu menu = new Menu();
                menu.setName(cursor.getString(0));
                menu.setRest(cursor.getString(1));
                menu.setDesc(cursor.getString(4));
                menu.setUrl(cursor.getInt(2));
                menu.setPrice(cursor.getFloat(5));
                menu.setTotalInCart(cursor.getInt(3));
                itemsInCartList.add(menu);
            }


        }
    }
}