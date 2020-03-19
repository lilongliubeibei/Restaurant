package com.example.yiliedurestaurant.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.model.Orders;
import com.example.yiliedurestaurant.ui.activity.OrdersDetailsActivity;

import java.util.List;

public class OrderListRecycleViewAdapter extends RecyclerView.Adapter<OrderListRecycleViewAdapter.ViewHolder> {
    List<Orders> orders_List;
    Context context;

    public OrderListRecycleViewAdapter(List<Orders> orders_List, Context context) {
        this.orders_List = orders_List;
        this.context = context;

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
         TextView tv_foods;
         TextView tv_toatalprice;
        View orderView;

        public ViewHolder(@NonNull View view) {
            super(view);
            orderView=view;//保存子项最外层布局实例
            tv_foods = (TextView) view.findViewById(R.id.tv_foods);
            tv_toatalprice = (TextView) view.findViewById(R.id.tv_toatalprice);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListRecycleViewAdapter.ViewHolder viewHolder, int position) {
        final Orders orders = orders_List.get(position);
        viewHolder.tv_foods.setText(orders.getDes());
        viewHolder.tv_toatalprice.setText(String.valueOf(orders.getPrice()));
        viewHolder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrdersDetailsActivity.class);
                intent.putExtra("orderId",orders.getOrderId());
                intent.putExtra("loginUserName",orders.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders_List.size();
    }
}
