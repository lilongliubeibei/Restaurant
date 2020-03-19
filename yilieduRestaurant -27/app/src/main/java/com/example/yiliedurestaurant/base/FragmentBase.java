package com.example.yiliedurestaurant.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yiliedurestaurant.Interface.viewInterface;


public class FragmentBase<T extends PrensenterBase> extends Fragment implements viewInterface {
    private ProgressDialog mLoadingDialog;
    protected T prensenterBase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = new ProgressDialog(getActivity());
        mLoadingDialog.setMessage("加载中");
        mLoadingDialog.setCancelable(false);
    }
    @Override
    public void toast(final String str, final Activity activity) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void startLoadinProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null) {
                    mLoadingDialog.show();
                }
            }
        });
    }
    @Override
    public void stopLoadinProgress() {
        getActivity().runOnUiThread(new Runnable() {
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
