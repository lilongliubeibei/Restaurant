package com.example.yiliedurestaurant.presenter;

import android.os.Message;

import com.example.yiliedurestaurant.base.PrensenterBase;
import com.example.yiliedurestaurant.model.Bean_MomentList;
import com.example.yiliedurestaurant.ui.fragment.Fragment2;
import com.example.yiliedurestaurant.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fm2Presenter extends PrensenterBase<Fragment2>  {

    public void lodMoment(String username, int page) {
        activityBase.startLoadinProgress();
        String url = "https://yiliedu.cn/app/getMomentList?page=" + page + "&limit=5" + "&username=" + username;
        HttpUtil.OkHttpRequestGet(url, new Callback() {
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
                Bean_MomentList bean_momentList = gson.fromJson(responsedata, Bean_MomentList.class);
                int code = bean_momentList.getInfo().getCode();
                if (!(code == 0)) {
                    activityBase.toast(bean_momentList.getInfo().getMsg(), activityBase.getActivity());
                } else {
                    if (bean_momentList.getMomentList() == null || bean_momentList.getMomentList().size() == 0) {
                        activityBase.toast("没有新状态了", activityBase.getActivity());
                    } else {
                        activityBase.updateMomentList(bean_momentList);
                    }
                }
            }
        });
    }
}
