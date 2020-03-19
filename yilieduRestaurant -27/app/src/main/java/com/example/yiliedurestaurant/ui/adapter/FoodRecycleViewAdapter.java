package com.example.yiliedurestaurant.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.model.Food;
import com.example.yiliedurestaurant.ui.activity.FoodDetailsActivity;
import com.example.yiliedurestaurant.ui.activity.MyActivity;
import com.example.yiliedurestaurant.ui.fragment.Fragment1;

import java.util.List;

public class FoodRecycleViewAdapter extends RecyclerView.Adapter<FoodRecycleViewAdapter.ViewHolder> {

    List<Food> food_List;
    Fragment1 fragment1;
    Context context;

    public FoodRecycleViewAdapter(List<Food> food_List, MyActivity myActivity) {
        this.food_List = food_List;
        this.context= myActivity;
        this.fragment1 = myActivity.fragment1;

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_food;
        ImageView iv_add;
        ImageView iv_sub;
        TextView tv_foodname;
        TextView tv_fooddetails;
        TextView tv_foodprice;
        TextView tv_foodnumber;

        public ViewHolder(@NonNull View view) {
            super(view);
            iv_food = (ImageView) view.findViewById(R.id.iv_food);
            iv_add = (ImageView) view.findViewById(R.id.iv_add);
            iv_sub = (ImageView) view.findViewById(R.id.iv_sub);
            tv_foodname = (TextView) view.findViewById(R.id.tv_foodname);
            tv_fooddetails = (TextView) view.findViewById(R.id.tv_fooddetails);
            tv_foodprice = (TextView) view.findViewById(R.id.tv_foodprice);
            tv_foodnumber = (TextView) view.findViewById(R.id.tv_foodnumber);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodRecycleViewAdapter.ViewHolder viewHolder, int position) {
        final Food food = food_List.get(position);
        Glide.with(context).load(food.getImgUrl()).into(viewHolder.iv_food);
        viewHolder.iv_add.setImageResource(R.mipmap.add);
        viewHolder.iv_sub.setImageResource(R.mipmap.sub);
        viewHolder.tv_foodname.setText(food.getName());
        viewHolder.tv_fooddetails.setText(food.getDes());
        final Float foodPrice = food.getPrice();
        viewHolder.tv_foodprice.setText(String.valueOf(food.getPrice()));
        viewHolder.tv_foodnumber.setText(String.valueOf(food.getCount()));
        Log.d("asdfg", "String.valueOf(food.getCount()= "+food.getCount()+"和"+String.valueOf(food.getCount()));
        viewHolder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j=Integer.parseInt(viewHolder.tv_foodnumber.getText().toString());
                food.setCount(++j);
                viewHolder.tv_foodnumber.setText(String.valueOf(food.getCount()));
                fragment1.food_totalmoney += (Float) food.getPrice();
                fragment1.food_number++;
                fragment1.tv_number.setText("数量：" + fragment1.food_number);
                fragment1.tv_totalmoney.setText("总价：" + fragment1.food_totalmoney + "元");
            }
        });
        viewHolder.tv_fooddetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FoodDetailsActivity.class);
                intent.putExtra("foodId", food.getFoodId());
                context.startActivity(intent);
            }
        });
        viewHolder.iv_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=Integer.parseInt(viewHolder.tv_foodnumber.getText().toString());
                if (i < 1) {
                    return;
                }
                food.setCount(--i);
                viewHolder.tv_foodnumber.setText(String.valueOf(food.getCount()));
                fragment1.food_totalmoney -= (Float) food.getPrice();
                fragment1.food_number--;
                fragment1.tv_number.setText("数量：" + fragment1.food_number);
                fragment1.tv_totalmoney.setText("总价：" + fragment1.food_totalmoney + "元");
            }
        });
    }

    @Override
    public int getItemCount() {
        return food_List.size();
    }
}
