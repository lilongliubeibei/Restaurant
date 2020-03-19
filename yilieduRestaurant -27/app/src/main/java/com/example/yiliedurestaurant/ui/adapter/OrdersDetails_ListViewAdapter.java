package com.example.yiliedurestaurant.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.model.OrderItem;

import java.util.List;

public class OrdersDetails_ListViewAdapter extends BaseAdapter {
    private Context context;
    List<OrderItem> orderItemList;

    public OrdersDetails_ListViewAdapter(Context context, List<OrderItem> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }

    @Override
    public int getCount() {
        return orderItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View view;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.orderitem, viewGroup, false);
            viewHolder.tv_foodName = (TextView) view.findViewById(R.id.tv_foodname);
            viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_count);
            viewHolder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            view.setTag(viewHolder);
        } else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_foodName.setText(orderItemList.get(i).getFoodName());
        viewHolder.tv_count.setText(String.valueOf(orderItemList.get(i).getCount())+"份");
        viewHolder.tv_price.setText(String.valueOf(orderItemList.get(i).getPrice())+"元/份");
        return view;
    }

    private static class ViewHolder {
        private TextView tv_foodName;
        private TextView tv_count;
        private TextView tv_price;
    }

}