package com.example.yiliedurestaurant.presenter;

import android.util.Log;

import com.example.yiliedurestaurant.base.PrensenterBase;
import com.example.yiliedurestaurant.model.Bean_login;
import com.example.yiliedurestaurant.ui.activity.LoginActivity;
import com.example.yiliedurestaurant.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginPresenter extends PrensenterBase<LoginActivity> {


    public void login(String username, String password) {
        Log.d("login", username);
        Log.d("login", password);

        HttpUtil.OkHttpRequestGet("https://yiliedu.cn/app/login?username=" + username + "&password=" + password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activityBase.stopLoadinProgress();
                activityBase.toast("网络连接失败", activityBase);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsedata = response.body().string();
                Gson gson = new Gson();
                Bean_login bean_login = gson.fromJson(responsedata, Bean_login.class);
                int code = bean_login.getInfo().getCode();
                if (!(code == 0)) {
                    activityBase.toast(bean_login.getInfo().getMsg(), activityBase);
                } else {
                    activityBase.LogingSuccess();
                }
            }
        });
    }
}
