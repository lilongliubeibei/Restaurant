package com.example.yiliedurestaurant.presenter;

import com.example.yiliedurestaurant.base.PrensenterBase;
import com.example.yiliedurestaurant.model.Bean_CreatOrder;
import com.example.yiliedurestaurant.model.Bean_Food;
import com.example.yiliedurestaurant.ui.fragment.Fragment1;
import com.example.yiliedurestaurant.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Fm1Presenter extends PrensenterBase<Fragment1> {

    //下拉刷新食物列表
    public void refreshFood() {
        activityBase.startLoadinProgress();

        String url = "https://yiliedu.cn/app/foodList?page=1&limit=5";
        HttpUtil.OkHttpRequestGet(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                activityBase.stopLoadinProgress();
                activityBase.toast("网络连接失败", activityBase.getActivity());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                activityBase.stopLoadinProgress();
                String responsedata = response.body().string();
                Gson gson = new Gson();
                Bean_Food bean_food = gson.fromJson(responsedata, Bean_Food.class);
                activityBase.updateFoodList(bean_food);
            }
        });
    }

    //加载食物列表
    public void lodFood(int page) {
        activityBase.startLoadinProgress();
        String url = "https://yiliedu.cn/app/foodList?page=" + page + "&limit=5";
        HttpUtil.OkHttpRequestGet(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                activityBase.stopLoadinProgress();
                activityBase.toast("网络连接失败", activityBase.getActivity());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                activityBase.stopLoadinProgress();
                String responsedata = response.body().string();
                Gson gson = new Gson();
                Bean_Food bean_food = gson.fromJson(responsedata, Bean_Food.class);
                activityBase.updateFoodList(bean_food);
            }
        });
    }
    //创建订单
        public void creatOrder(String username,String str){
            String url = "https://yiliedu.cn/app/createOrder";
        RequestBody requestBody = new FormBody.Builder().add("username", username).add("orderItems", str).build();
        HttpUtil.OkHttpRequestPost(url, requestBody, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                activityBase.stopLoadinProgress();
                activityBase.toast("网络连接失败",  activityBase.getActivity());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                activityBase.stopLoadinProgress();
                String responsedata = response.body().string();
                Gson gson1 = new Gson();
                Bean_CreatOrder bean_CreatOrder = gson1.fromJson(responsedata, Bean_CreatOrder.class);
                int code = bean_CreatOrder.getInfo().getCode();
                String msg = bean_CreatOrder.getInfo().getMsg();
                if (code != 0) {
                    activityBase.toast(bean_CreatOrder.getInfo().getMsg(), activityBase.getActivity());
                } else {
                    activityBase.toast("创建订单成功，可去订单中心查询", activityBase.getActivity());
                    activityBase.clearData();
                    activityBase.updateMoney();
                    activityBase.updateOrder();
                }
            }
        });
    }
}
