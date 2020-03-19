package com.example.yiliedurestaurant.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GetLoginState {
    public static boolean getLoginState(Context context){
        SharedPreferences sp = context.getSharedPreferences("loginInfo", 0);
        return sp.getBoolean("isLogin", false);
    }
    public static String getUsername(Context context){
        SharedPreferences sp = context.getSharedPreferences("loginInfo", 0);
        return sp.getString("loginUserName", "");
    }
}
