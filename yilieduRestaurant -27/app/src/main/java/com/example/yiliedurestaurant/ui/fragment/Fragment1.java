package com.example.yiliedurestaurant.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.base.FragmentBase;
import com.example.yiliedurestaurant.model.Bean_Food;
import com.example.yiliedurestaurant.model.Food;
import com.example.yiliedurestaurant.model.FoodOrderItem;
import com.example.yiliedurestaurant.presenter.Fm1Presenter;
import com.example.yiliedurestaurant.ui.activity.LoginActivity;
import com.example.yiliedurestaurant.ui.activity.MyActivity;
import com.example.yiliedurestaurant.ui.adapter.FoodRecycleViewAdapter;
import com.example.yiliedurestaurant.utils.GetLoginState;
import com.example.yiliedurestaurant.utils.refresh.SwipeRefresh;
import com.example.yiliedurestaurant.utils.refresh.SwipeRefreshLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends FragmentBase<Fm1Presenter> {
    MyActivity myActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private RecyclerView recyclerView;
    private FoodRecycleViewAdapter foodRecycleViewAdapter;
    List<Food> food_List = new ArrayList<>();
    List<Food> tempfood_list;
    List<FoodOrderItem> foodOrderList = new ArrayList<>();
    public TextView tv_number;
    public TextView tv_totalmoney;
    TextView tv_pay;
    public int food_number;
    public int food_totalmoney;
    String loginUserName;
    int page = 1;
    boolean loadOrRefrash;

    public Fragment1(MyActivity myActivity) {
        this.myActivity = myActivity;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        initRecyclerView();
        prensenterBase = new Fm1Presenter();
        prensenterBase.setActivityBase(myActivity.fragment1);
        prensenterBase.lodFood(page);
        return view;

    }


    private void initRecyclerView() {
        tv_number = view.findViewById(R.id.number);
        tv_totalmoney = view.findViewById(R.id.money);
        tv_pay = view.findViewById(R.id.pay);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadOrRefrash = true;
                prensenterBase.refreshFood();
            }
        });
        swipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                page += 1;
                loadOrRefrash = false;
                prensenterBase.lodFood(page);
                swipeRefreshLayout.setPullUpRefreshing(false);
            }
        });
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food_number == 0) {
                    myActivity.toast("请先选择菜品", myActivity);
                    return;
                }
                if (!GetLoginState.getLoginState(myActivity)) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    loginUserName = GetLoginState.getUsername(myActivity);
                    creatOrder();//创建订单
                }

            }
        });
    }


    //更新食物列表
    public void updateFoodList(final Bean_Food bean_food) {
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bean_food.getInfo().getCode() != 0) {
                    myActivity.toast(bean_food.getInfo().getMsg(), myActivity);
                } else {
                    if (loadOrRefrash) {
                        food_List.clear();
                    }
                    tempfood_list = bean_food.getFoodList();
                    for (Food food : tempfood_list) {
                        food.setCount(0);
                    }
                    if (tempfood_list == null || tempfood_list.size() == 0) {
                        myActivity.toast("没有新食物了", myActivity);
                    } else {
                        food_List.addAll(tempfood_list);
                        if (foodRecycleViewAdapter == null) {
                            foodRecycleViewAdapter = new FoodRecycleViewAdapter(food_List, myActivity);
                            recyclerView.setAdapter(foodRecycleViewAdapter);
                        } else {
                            foodRecycleViewAdapter.notifyDataSetChanged();
                            if (myActivity.currentFragment == myActivity.fragment1) {
                                myActivity.toast("为您找到了" + tempfood_list.size() + "个新食物", myActivity);
                            }
                        }
                    }
                }
            }
        });
    }

    //创建订单
    public void creatOrder() {
        foodOrderList.clear();
        String str = "";
        for (Food food : food_List) {
            if (food.getCount() > 0) {
                FoodOrderItem foodOrderItem = new FoodOrderItem(food.getFoodId(), food.getCount());
                foodOrderList.add(foodOrderItem);
            }
        }
        Gson gson = new Gson();
        if (foodOrderList != null) {
            str = gson.toJson(foodOrderList);
        }
        prensenterBase.creatOrder(loginUserName, str);
    }

    public void clearData() {
        if (tempfood_list != null && tempfood_list.size() != 0) {
            tempfood_list.clear();
        }
        for (Food food : food_List) {
            food.setCount(0);
        }
        food_number = 0;
        food_totalmoney = 0;
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_number.setText("数量：0");
                tv_totalmoney.setText("总价：0元");
                foodRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }


    public void updateMoney() {
        if (myActivity.fragment4 != null) {
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myActivity.fragment4.updateMoney();
                }
            });
        }
    }


    public void updateOrder() {
        if (myActivity.fragment4 != null) {
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myActivity.fragment4.refreshOrder();
                }
            });
        }
    }
}
