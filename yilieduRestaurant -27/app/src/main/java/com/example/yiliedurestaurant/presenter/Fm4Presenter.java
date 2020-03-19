package com.example.yiliedurestaurant.presenter;

import android.os.Message;

import com.example.yiliedurestaurant.base.PrensenterBase;
import com.example.yiliedurestaurant.model.Bean_Orders;
import com.example.yiliedurestaurant.model.Bean_UserDetail;
import com.example.yiliedurestaurant.ui.fragment.Fragment4;
import com.example.yiliedurestaurant.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Fm4Presenter extends PrensenterBase<Fragment4> {

    //加载订单列表
    public void lodOrder(String str, int page) {
        activityBase.startLoadinProgress();
        String url = "https://yiliedu.cn/app/getOrderList?username=" + str + "&page=" + page + "&limit=5";
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
                Bean_Orders bean_orders = gson.fromJson(responsedata, Bean_Orders.class);
                int code = bean_orders.getInfo().getCode();
                String msg = bean_orders.getInfo().getMsg();
                if (!(code == 0)) {
                    activityBase.toast(bean_orders.getInfo().getMsg(), activityBase.getActivity());
                } else {
                    activityBase.updateOrderList(bean_orders);
                }
            }
        });
    }


    //充值方法，充值成功后更新余额
    public void addmoney(String str, int balance) {
        activityBase.startLoadinProgress();
        String url = "https://yiliedu.cn/app/deposit?username=" + str + "&balance=" + balance;
        RequestBody requestBody = new FormBody.Builder().add("username", str).add("balance", String.valueOf(balance)).build();
        HttpUtil.OkHttpRequestPost(url, requestBody, new Callback() {
            Message message = Message.obtain();

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
                Bean_UserDetail bean_UserDetail = gson.fromJson(responsedata, Bean_UserDetail.class);
                int code = bean_UserDetail.getInfo().getCode();
                String msg = bean_UserDetail.getInfo().getMsg();
                if (!(code == 0)) {
                    activityBase.toast(msg, activityBase.getActivity());
                } else {
                    activityBase.updateMoney(bean_UserDetail);
                    activityBase.toast("充值成功", activityBase.getActivity());
                }
            }
        });
    }

    //加载余额方法
    public void lodmoney(String usename) {
        activityBase.startLoadinProgress();
        String url = "https://yiliedu.cn/app/getUserDetail?username=" + usename;
        HttpUtil.OkHttpRequestGet(url, new Callback() {
            Message message = Message.obtain();

            @Override
            public void onFailure(Call call, IOException e) {
                activityBase.stopLoadinProgress();
                activityBase.toast("网络连接失败", activityBase.getActivity());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsedata = response.body().string();
                Gson gson = new Gson();
                Bean_UserDetail bean_UserDetail = gson.fromJson(responsedata, Bean_UserDetail.class);
                int code = bean_UserDetail.getInfo().getCode();
                String msg = bean_UserDetail.getInfo().getMsg();
                if (!(code == 0)) {
                    activityBase.toast(msg, activityBase.getActivity());
                } else {
                    activityBase.updateMoney(bean_UserDetail);
                }
            }
        });
    }
}
