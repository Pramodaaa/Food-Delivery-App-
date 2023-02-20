package com.example.foodtogo.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodtogo.Globals;
import com.example.foodtogo.R;
import com.example.foodtogo.model.OrderHistoryModel;
import java.util.ArrayList;
import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{

    //    private List<Menu> menuList;
    private List<OrderHistoryModel> orderHistoryModelList;
    Globals sharedData;

    public ProfileAdapter(List<OrderHistoryModel> orderHistoryModelList) {
        this.orderHistoryModelList = orderHistoryModelList;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler_item, parent, false);
        sharedData = Globals.getInstance();
        return new ProfileViewHolder(view);
    }


    // to recreate a row of the order history
    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menuName.setText(orderHistoryModelList.get(position).getName());
        holder.restaurantName.setText(orderHistoryModelList.get(position).getRest());
        holder.menuPrice.setText("Item count " +orderHistoryModelList.get(position).getCount());
        holder.thumbImage.setImageResource(orderHistoryModelList.get(position).getImg());

        // make the rest invisible as its reading for the cart list
        holder.imageAddOne.setVisibility(View.GONE);
        holder.date.setVisibility(View.GONE);
        holder.imageMinus.setVisibility(View.GONE);
        holder.removeFromCart.setVisibility(View.GONE);


    }

    // returns the no of data elements
    @Override
    public int getItemCount() {
        if(orderHistoryModelList == null) {
            orderHistoryModelList = new ArrayList<>();
        }
        return orderHistoryModelList.size();
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        TextView date;
        TextView menuName;
        TextView  menuPrice;
        ImageButton removeFromCart;
        ImageView thumbImage;
        ImageButton imageMinus;
        ImageButton imageAddOne;
        TextView  tvCount;

        public ProfileViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurantNameText);
            date = view.findViewById(R.id.dateTimeText);
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