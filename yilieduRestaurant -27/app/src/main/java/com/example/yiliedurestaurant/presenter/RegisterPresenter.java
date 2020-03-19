package com.example.yiliedurestaurant.presenter;

import android.os.Message;
import android.util.Log;
import com.example.yiliedurestaurant.base.PrensenterBase;
import com.example.yiliedurestaurant.model.Bean_UserDetail;
import com.example.yiliedurestaurant.ui.activity.RegisterActivity;
import com.example.yiliedurestaurant.utils.HttpUtil;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterPresenter extends PrensenterBase<RegisterActivity> {


    public void register(final String username, String password) {
        RequestBody requestBody = new FormBody.Builder().add("username", username).add("password", password).build();
        HttpUtil.OkHttpRequestPost("https://yiliedu.cn/app/reg?username=" + username + "&password=" + password, requestBody, new Callback() {
            Message message = Message.obtain();
            @Override
            public void onFailure(Call call, IOException e) {
                activityBase.stopLoadinProgress();
                activityBase.toast("网络连接失败", activityBase);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsedata = response.body().string();
                Log.d("asdf", "responsedata:" + responsedata);
                Gson gson = new Gson();
                Bean_UserDetail bean_UserDetail = gson.fromJson(responsedata, Bean_UserDetail.class);
                int code = bean_UserDetail.getInfo().getCode();
                String msg = bean_UserDetail.getInfo().getMsg();

                Log.d("asdf", "msg:" + msg);
                Log.d("asdf", "code:" + code);
                if (!(code == 0)) {
                    activityBase.toast(bean_UserDetail.getInfo().getMsg(), activityBase);
                } else {
                    activityBase.registerSuccess(bean_UserDetail);
                }
            }
        });
    }
}
