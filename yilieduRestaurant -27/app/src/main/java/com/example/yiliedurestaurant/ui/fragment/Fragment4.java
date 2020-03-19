package com.example.yiliedurestaurant.ui.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.base.FragmentBase;
import com.example.yiliedurestaurant.model.Bean_Orders;
import com.example.yiliedurestaurant.model.Bean_UserDetail;
import com.example.yiliedurestaurant.model.Orders;
import com.example.yiliedurestaurant.presenter.Fm4Presenter;
import com.example.yiliedurestaurant.ui.activity.MyActivity;
import com.example.yiliedurestaurant.ui.activity.MyDialog;
import com.example.yiliedurestaurant.Interface.MyDialogInterface;
import com.example.yiliedurestaurant.ui.adapter.OrderListRecycleViewAdapter;
import com.example.yiliedurestaurant.utils.refresh.SwipeRefresh;
import com.example.yiliedurestaurant.utils.refresh.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


public class Fragment4 extends FragmentBase<Fm4Presenter> implements MyDialogInterface {
    TextView tv_backlogin, tv_addmoney, tv_username, tv_money;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    MyActivity myActivity;
    private RecyclerView recyclerView;
    public OrderListRecycleViewAdapter orderListRecycleViewAdapter;
    int page = 1;
    public String loginUserName;
    List<Orders> orders_List = new ArrayList<>();
    boolean loadOrRefrash;

    public Fragment4(MyActivity myActivity) {
        this.myActivity = myActivity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);
        initRecyclerView();
        prensenterBase = new Fm4Presenter();
        prensenterBase.setActivityBase(myActivity.fragment4);
        prensenterBase.lodmoney(loginUserName);
        prensenterBase.lodOrder(loginUserName, page);
        return view;
    }


    private void initRecyclerView() {
        tv_backlogin = view.findViewById(R.id.tv_backlogin);
        tv_addmoney = view.findViewById(R.id.tv_addmoney);
        tv_username = view.findViewById(R.id.tv_username);
        tv_money = view.findViewById(R.id.tv_money);
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", 0);
        loginUserName = sp.getString("loginUserName", "用户名");
        tv_username.setText(loginUserName);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                refreshOrder();
            }
        });
        swipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                page += 1;
                loadOrRefrash = false;
                prensenterBase.lodOrder(loginUserName, page);
                swipeRefreshLayout.setPullUpRefreshing(false);
            }
        });
        tv_backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
        tv_addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog myDialog = new MyDialog(getContext(), R.style.DialogTheme);
                myDialog.setMyDialogInterface(myActivity.fragment4);
                myDialog.show();
            }
        });
    }

    public void refreshOrder() {
        orders_List.clear();
        page = 1;
        loadOrRefrash = true;
        prensenterBase.lodOrder(loginUserName, page);
    }


    public void createDialog() {
        //实例话AlertDialog构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //设置提示信息
        builder.setMessage("确认退出登录吗？");
        //设置提示标题
        builder.setTitle("提示");
        //设置点击按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击确定则对话框消失，并退出程序Ps（单个Activity可以用finish（）结束，但多个就不行了）
                //多个Activity可以用System.exit(0),退出虚拟机
                //还可以用android.os.Process.killProcess(android.os.Process.myPid())来结束当前进程
                SharedPreferences.Editor editor = getContext().getSharedPreferences("loginInfo", 0).edit();
                //获取编辑器
//                editor.putBoolean("isLogin", false);
                editor.clear();
                editor.apply();
                Log.d("zxc", "退出登录" + false);
                dialog.dismiss();
                if (myActivity.fragment3 == null) {
                    myActivity.fragment3 = new Fragment3(myActivity);
                }
                myActivity.showFragment(myActivity.fragment3);
                myActivity.fragment4=null;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击取消，消失对话框
                dialog.dismiss();
            }
        });
        //创建提示框并显示，调用show()方法
        builder.create().show();
    }


    public void updateOrderList(final Bean_Orders bean_orders) {
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bean_orders.getOrders() == null & bean_orders.getOrders().size() == 0) {
                    myActivity.toast("没有订单了", myActivity);
                } else {
                    if (orderListRecycleViewAdapter == null) {
                        orderListRecycleViewAdapter = new OrderListRecycleViewAdapter(orders_List, myActivity);
                        recyclerView.setAdapter(orderListRecycleViewAdapter);
                    }
                    orders_List.addAll(bean_orders.getOrders());
                    orderListRecycleViewAdapter.notifyDataSetChanged();
                    myActivity.toast("为您找到了" + bean_orders.getOrders().size() + "个新订单", myActivity);
                }
            }
        });
    }

    public void updateMoney(final Bean_UserDetail bean_userDetail) {
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_money.setText("余额：" + bean_userDetail.getUser().getBalance() + "元");
            }

        });
    }

    public void updateMoney() {
        prensenterBase.lodmoney(loginUserName);
    }

    @Override
    public void addmoney(int balance) {
        prensenterBase.addmoney(loginUserName, balance);
    }
}

