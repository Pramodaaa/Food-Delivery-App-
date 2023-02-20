package com.example.foodtogo.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtogo.Globals;
import com.example.foodtogo.R;
import com.example.foodtogo.database.DBHelper;
import com.example.foodtogo.fragments.RestaurantMenuFragment;
import com.example.foodtogo.fragments.SigninFragment;
import com.example.foodtogo.model.Menu;
import com.example.foodtogo.model.OrderHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder>{

    private List<Menu> menuList;
    private List<OrderHistoryModel> orderHistoryModelList;
    Globals sharedData;
    DBHelper DB;

    public CartListAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler_item, parent, false);
        sharedData = Globals.getInstance();
        DB = new DBHelper(view.getContext());
        return new CartViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.thumbImage.setImageResource(menuList.get(position).getUrl());
        holder.restaurantName.setText(menuList.get(position).getRest());
        holder.desc.setText(menuList.get(position).getDesc());
        holder.menuPrice.setText("Price: LKR "+menuList.get(position).getPrice());
        holder.tvCount.setText(menuList.get(position).getTotalInCart()+"");
        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
                sharedData.setCartList(menuList);
                DB.deleteCartItem(menuList.get(position).getName());
                menuList.remove(menuList.get(position));
//                Menu menu  = menuList.get(position);
//                menu.setTotalInCart(1);
//                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.removeFromCart.setVisibility(View.GONE);
//                holder.tvCount.setText(menu.getTotalInCart()+"");
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
                    DB.updateCart(menu,total);
                    sharedData.setCartList(menuList);
                    holder.tvCount.setText(total +"");
                } else {
//                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.removeFromCart.setVisibility(View.VISIBLE);
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
                    DB.updateCart(menu,total);
                    sharedData.setCartList(menuList);
                    holder.tvCount.setText(total +"");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(menuList == null) {
            menuList = new ArrayList<>();
        }
        return menuList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        TextView desc;
        TextView menuName;
        TextView  menuPrice;
        ImageButton  removeFromCart;
        ImageView thumbImage;
        ImageButton imageMinus;
        ImageButton imageAddOne;
        TextView  tvCount;

        public CartViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurantNameText);
            desc = view.findViewById(R.id.dateTimeText);
            menuName = view.findViewById(R.id.foodNameText);
            menuPrice = view.findViewById(R.id.priceText);
            removeFromCart = view.findViewById(R.id.removeFromCartBtn);
            thumbImage = view.findViewById(R.id.foodImageView);
            imageMinus = view.findViewById(R.id.removeBtn);
            imageAddOne = view.findViewById(R.id.addBtn);
            tvCount = view.findViewById(R.id.amountTextNumber);

        }
    }

}
