package com.example.yiliedurestaurant.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yiliedurestaurant.Interface.viewInterface;


public class ActivityBase<T extends PrensenterBase> extends AppCompatActivity implements viewInterface {
    private ProgressDialog mLoadingDialog;
    protected T prensenterBase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setMessage("加载中");
        mLoadingDialog.setCancelable(false);
    }

    public void toast(final String str, final Activity activity) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startLoadinProgress() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void stopLoadinProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        prensenterBase.release();
    }
}
