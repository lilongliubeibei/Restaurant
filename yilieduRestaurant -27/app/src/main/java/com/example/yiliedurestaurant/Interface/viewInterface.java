package com.example.yiliedurestaurant.Interface;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public interface viewInterface {
    void onCreate(@Nullable Bundle savedInstanceState);
    void toast(final String str, final Activity activity);
    void startLoadinProgress();
    void stopLoadinProgress();
}
