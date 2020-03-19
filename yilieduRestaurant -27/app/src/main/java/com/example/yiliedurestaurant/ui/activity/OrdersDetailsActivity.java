package com.example.yiliedurestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.base.ActivityBase;
import com.example.yiliedurestaurant.model.Bean_OrderDetails;
import com.example.yiliedurestaurant.model.OrderItem;
import com.example.yiliedurestaurant.presenter.OrderDetailsPresenter;
import com.example.yiliedurestaurant.ui.adapter.OrdersDetails_ListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class OrdersDetailsActivity extends ActivityBase<OrderDetailsPresenter> {
    ListView listView;
    TextView textView;
    List<OrderItem> orderItemList = new ArrayList<>();
    int orderId;
    String loginUserName;
    OrdersDetails_ListViewAdapter ordersDetails_ListViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("订单详情");
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", 0);
        loginUserName = intent.getStringExtra("loginUserName");
        initView();
        prensenterBase = new OrderDetailsPresenter();
        prensenterBase.setActivityBase(this);
        prensenterBase.getOrderDetail(loginUserName, orderId);
    }

    private void initView() {
        listView = findViewById(R.id.list_view);
        textView = findViewById(R.id.tv_totalmoney);
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void openOrderDetail(final Bean_OrderDetails bean_orderDetails) {
        Log.d("price", String.valueOf(bean_orderDetails.getOrders().getPrice()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ordersDetails_ListViewAdapter == null) {
                    orderItemList = bean_orderDetails.getOrders().getOrderItem();
                    ordersDetails_ListViewAdapter = new OrdersDetails_ListViewAdapter(OrdersDetailsActivity.this, orderItemList);
                    listView.setAdapter(ordersDetails_ListViewAdapter);
                }
                ordersDetails_ListViewAdapter.notifyDataSetChanged();
                Log.d("price", String.valueOf(bean_orderDetails.getOrders().getPrice()));
                textView.setText(bean_orderDetails.getOrders().getPrice() + "元");
            }
        });
    }
}
