package com.example.foodtogo.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtogo.R;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.model.Menu;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyHomeViewHolder> {

    private List<Menu> menuList;
    DBHelper DB;

    public HomeListAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MyHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return new HomeListAdapter.MyHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHomeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.addToCartButton.setVisibility(View.GONE);
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price: LKR "+menuList.get(position).getPrice());
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB = new DBHelper(v.getContext());
                Menu menu  = menuList.get(position);
                menu.setTotalInCart(1);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.tvCount.setText(menu.getTotalInCart()+"");
//                DB.addToCart(menu.getName(),menu.getDesc(),menu.getUrl(),menu.getPrice(),menu.getTotalInCart());
            }
        });
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total--;

                if(total > 0 ) {
                    menu.setTotalInCart(total);
                    holder.tvCount.setText(total +"");
                } else {
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    menu.setTotalInCart(total);
                }
            }
        });

        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total++;
                if(total <= 10 ) {
                    menu.setTotalInCart(total);
                    holder.tvCount.setText(total +"");
                }
            }
        });

        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyHomeViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView  menuPrice;
        TextView  addToCartButton;
        ImageView thumbImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView  tvCount;
        LinearLayout addMoreLayout;

        public MyHomeViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            thumbImage = view.findViewById(R.id.thumbImage);
            imageMinus = view.findViewById(R.id.imageMinus);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            tvCount = view.findViewById(R.id.tvCount);

            addMoreLayout  = view.findViewById(R.id.addMoreLayout);
        }
    }

}
