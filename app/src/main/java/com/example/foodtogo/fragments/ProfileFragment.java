package com.example.foodtogo.fragments;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtogo.Globals;
import com.example.foodtogo.R;
import com.example.foodtogo.adapters.CartListAdapter;
import com.example.foodtogo.adapters.ProfileAdapter;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.model.Menu;
import com.example.foodtogo.model.OrderHistoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView email;
    private Button logout;
    Globals sharedData;
    private List<OrderHistoryModel> itemsOrderList; //  a list of order history
    private RecyclerView recyclerView;
    DBHelper DB;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedData = Globals.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedData.getValue()) {
                    sharedData.setValue(false);
                    sharedData.setCartList(null);
                    sharedData.setUser(null);

                    HomeFragment homeFragment = new HomeFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout,homeFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.userEmail);
        logout = view.findViewById(R.id.logoutBtn);

        DB = new DBHelper(view.getContext());
        itemsOrderList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.pastOrderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ProfileAdapter listAdapter = new ProfileAdapter(itemsOrderList);
        recyclerView.setAdapter(listAdapter);

        if(!sharedData.getValue()) {
            logout.setVisibility(View.GONE);
        }

        if(sharedData.getValue()){
            email.setText(sharedData.getUser().toString());
            displayOrderHistoryData();
        } else {
            email.setText("User not logged yet");
        }

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(sharedData.getValue()) {
//                    sharedData.setValue(false);
//                    sharedData.setCartList(null);
//                    sharedData.setUser(null);
//                } else {
//                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    // display the order history
    void displayOrderHistoryData() {
        Cursor cursor = DB.readOrderHistoryData(sharedData.getUser().toString());
        if(cursor.getCount() == 0) {
            return;
        } else {

            while (cursor.moveToNext()) { // cursing through the shared data
                OrderHistoryModel orderHistoryModel = new OrderHistoryModel();
                orderHistoryModel.setName(cursor.getString(3));
                orderHistoryModel.setCount(cursor.getInt(4));
                orderHistoryModel.setDateTime(cursor.getString(1));
                orderHistoryModel.setUser(cursor.getString(0));
                orderHistoryModel.setImg(cursor.getInt(5));
                itemsOrderList.add(orderHistoryModel); // add the list
            }
        }
    }
}