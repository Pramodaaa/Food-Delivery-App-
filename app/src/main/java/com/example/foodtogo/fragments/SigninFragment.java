package com.example.foodtogo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtogo.Globals;
import com.example.foodtogo.R;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.model.Menu;


public class SigninFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView helperText;
    private Button signBtn, accBtn;
    private EditText email, password;
    DBHelper DB;
    Globals sharedData;

    public SigninFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signBtn = view.findViewById(R.id.loginBtn);
        accBtn = view.findViewById(R.id.loginSwitchBtn);
        email = view.findViewById(R.id.editTextTextEmailAddress);
        password = view.findViewById(R.id.editTextTextPassword);
        helperText = view.findViewById(R.id.loginHelperText);

        DB = new DBHelper(view.getContext());
        sharedData = Globals.getInstance();

        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helperText.getText().equals("SignUp")) {
                    signBtn.setText("Sign-In");
                    accBtn.setText("For Registration! Click here");
                    helperText.setText("SignIn");

                } else if(helperText.getText().equals("SignIn")) {
                    signBtn.setText("Sign-Up");
                    accBtn.setText("Already have an account! Click here :)");
                    helperText.setText("SignUp");
                }
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString();
                String pass = password.getText().toString();

                if(helperText.getText().equals("SignUp")) {

                    if(user.equals("")||pass.equals("")) {
                        Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else{
                            Boolean checkuser = DB.checkusername(user);
                            if(checkuser==false){
                                Boolean insert = DB.addUser(user, pass);
                                if(insert==true){
                                    Toast.makeText(getActivity(), "Registered and Logged-in Successfully", Toast.LENGTH_SHORT).show();
                                    sharedData.setValue(true);
                                    sharedData.setUser(user);
                                    for(Menu menu: sharedData.getCartList()) {
                                        DB.addToOrderHistory(menu.getName(),menu.getTotalInCart(),user,menu.getUrl());
                                    }
                                }else{
                                    Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getActivity(), "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                            }
                    }
                } else {
                    if(user.equals("")||pass.equals("")) {
                        Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkuser = DB.checkusernamepassword(user,pass);
                        if(checkuser) {
                            sharedData.setValue(true);
                            sharedData.setUser(user);
                            for(Menu menu: sharedData.getCartList()) {
                                DB.addToOrderHistory(menu.getName(),menu.getTotalInCart(),user,menu.getUrl());
                            }
                            Toast.makeText(getActivity(), "User Logged in successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Something wrong!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

    }
}