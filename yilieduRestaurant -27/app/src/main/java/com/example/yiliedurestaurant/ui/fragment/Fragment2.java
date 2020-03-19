package com.example.yiliedurestaurant.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.base.FragmentBase;
import com.example.yiliedurestaurant.model.Bean_MomentList;
import com.example.yiliedurestaurant.model.Moments;
import com.example.yiliedurestaurant.presenter.Fm2Presenter;
import com.example.yiliedurestaurant.ui.activity.MyActivity;
import com.example.yiliedurestaurant.ui.adapter.MomentRecycleViewAdapter;
import com.example.yiliedurestaurant.utils.refresh.SwipeRefresh;
import com.example.yiliedurestaurant.utils.refresh.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends FragmentBase<Fm2Presenter> {
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private RecyclerView recyclerView;
    private MomentRecycleViewAdapter momentRecycleViewAdapter;
    List<Moments> moments_List = new ArrayList<>();
    String loginUserName;
    int page = 1;
    boolean loadOrRefrash;
    MyActivity myActivity;

    public Fragment2(MyActivity myActivity) {
        this.myActivity = myActivity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, container, false);
        initRecyclerView();
        prensenterBase = new Fm2Presenter();
        prensenterBase.setActivityBase(myActivity.fragment2);
        prensenterBase.lodMoment(loginUserName, page);
        return view;
    }


    private void initRecyclerView() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        momentRecycleViewAdapter = new MomentRecycleViewAdapter(moments_List, getContext());
        recyclerView.setAdapter(momentRecycleViewAdapter);
        swipeRefreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", 0);
        //获取编辑器
        loginUserName = sp.getString("loginUserName", "用户名");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadOrRefrash = true;
                page = 1;
                prensenterBase.lodMoment(loginUserName, page);
            }
        });
        swipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadOrRefrash = false;
                page += 1;
                prensenterBase.lodMoment(loginUserName, page);
                swipeRefreshLayout.setPullUpRefreshing(false);
            }
        });
    }

    public void updateMomentList(final Bean_MomentList bean_momentList) {
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (momentRecycleViewAdapter == null) {
                    momentRecycleViewAdapter = new MomentRecycleViewAdapter(moments_List, myActivity);
                    recyclerView.setAdapter(momentRecycleViewAdapter);
                }
                if (loadOrRefrash) {
                    moments_List.clear();
                }
                moments_List.addAll(bean_momentList.getMomentList());
                momentRecycleViewAdapter.notifyDataSetChanged();
                myActivity.toast("为您找到了" + bean_momentList.getMomentList().size() + "个新状态", myActivity);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
