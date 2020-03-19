package com.example.yiliedurestaurant.presenter;

import android.os.Message;
import android.util.Log;

import com.example.yiliedurestaurant.base.PrensenterBase;
import com.example.yiliedurestaurant.model.Bean_OrderDetails;
import com.example.yiliedurestaurant.ui.activity.OrdersDetailsActivity;
import com.example.yiliedurestaurant.ui.fragment.Fragment4;
import com.example.yiliedurestaurant.utils.HttpUtil;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderDetailsPresenter extends PrensenterBase<OrdersDetailsActivity> {


    //加载订单详情
    public void getOrderDetail(String username, int orderId) {
        String url = "https://yiliedu.cn/app/getOrderDetail?username=" + username + "&orderId=" + orderId;
        Log.d("asd", "url=" + url);
        HttpUtil.OkHttpRequestGet(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                activityBase.stopLoadinProgress();
                activityBase.toast("网络连接失败", activityBase);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsedata = response.body().string();
                Gson gson = new Gson();
                Bean_OrderDetails bean_OrderDetails = gson.fromJson(responsedata, Bean_OrderDetails.class);
                if (bean_OrderDetails.getInfo().getCode() != 0) {
                    activityBase.toast(bean_OrderDetails.getInfo().getMsg(), activityBase);
                } else {
                    activityBase.openOrderDetail(bean_OrderDetails);
                }
            }
        });
    }

    public void release() {
        activityBase=null;
    }
}
