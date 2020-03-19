package com.example.yiliedurestaurant.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.ui.activity.MyActivity;
import com.example.yiliedurestaurant.ui.activity.LoginActivity;

public class Fragment3 extends Fragment {
    Button button;
    MyActivity myActivity;

    public Fragment3(MyActivity myActivity) {
        this.myActivity = myActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        button = view.findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, myActivity.current_location);
                Log.d("current_location", String.valueOf(myActivity.current_location));
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (myActivity.current_location == 222) {
            Log.d("current_location", "222");
            if (data != null) {
                if (myActivity.fragment2 == null) {
                    myActivity.fragment2 = new Fragment2(myActivity);
                }
                myActivity.showFragment(myActivity.fragment2);
            }
        }
        if (myActivity.current_location == 333) {
            Log.d("current_location", "333");
            if (data != null) {
                if (myActivity.fragment4 == null) {
                    myActivity.fragment4 = new Fragment4(myActivity);
                }
                myActivity.showFragment(myActivity.fragment4);
            }
        }
    }
}

