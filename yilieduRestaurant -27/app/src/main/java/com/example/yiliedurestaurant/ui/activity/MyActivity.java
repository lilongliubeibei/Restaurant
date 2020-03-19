package com.example.yiliedurestaurant.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.base.ActivityBase;
import com.example.yiliedurestaurant.ui.fragment.Fragment1;
import com.example.yiliedurestaurant.ui.fragment.Fragment2;
import com.example.yiliedurestaurant.ui.fragment.Fragment3;
import com.example.yiliedurestaurant.ui.fragment.Fragment4;
import com.example.yiliedurestaurant.utils.GetLoginState;

public class MyActivity extends ActivityBase implements View.OnClickListener {
    TextView tv_food, tv_communication, tv_me;
    ImageView iv_food, iv_communication, iv_me;
    public Fragment1 fragment1 = new Fragment1(this);
    public Fragment2 fragment2;
    public Fragment3 fragment3;
    public Fragment4 fragment4;
    FragmentManager fm;
    public int current_location; //用于设置是登录后显示哪个页面
    public Fragment currentFragment = fragment1;
    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section1);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment, fragment1).commit();
        initview();
        setClickListener();


    }

    private void setClickListener() {
        iv_food.setOnClickListener(this);
        iv_communication.setOnClickListener(this);
        iv_me.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_food:
                showFragment(fragment1);
                iv_food.setImageResource(R.mipmap.food_select);
                iv_me.setImageResource(R.mipmap.me_unselected);
                iv_communication.setImageResource(R.mipmap.communication_unselected);
                break;
            case R.id.iv_communication:
                current_location = 222;
                if (!GetLoginState.getLoginState(this)) {
                    if (fragment3 == null) {
                        fragment3 = new Fragment3(this);
                    }
                    showFragment(fragment3);
                } else {
                    if (fragment2 == null) {
                        fragment2 = new Fragment2(this);
                    }
                    showFragment(fragment2);
                }
                iv_food.setImageResource(R.mipmap.food_unselect);
                iv_me.setImageResource(R.mipmap.me_unselected);
                iv_communication.setImageResource(R.mipmap.communication_select);
                break;
            case R.id.iv_me:
                current_location = 333;
                if (!GetLoginState.getLoginState(this)) {
                    if (fragment3 == null) {
                        fragment3 = new Fragment3(this);
                    }
                    showFragment(fragment3);
                } else {
                    if (fragment4 == null) {
                        fragment4 = new Fragment4(this);
                    }
                    showFragment(fragment4);
                }
                iv_food.setImageResource(R.mipmap.food_unselect);
                iv_me.setImageResource(R.mipmap.me_select);
                iv_communication.setImageResource(R.mipmap.communication_unselected);
                break;
        }
    }

    private void initview() {
        tv_food = findViewById(R.id.food);
        tv_communication = findViewById(R.id.communication);
        tv_me = findViewById(R.id.me);
        iv_food = findViewById(R.id.iv_food);
        iv_communication = findViewById(R.id.iv_communication);
        iv_me = findViewById(R.id.iv_me);
    }

    public void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {//  判断传入的fragment是不是当前的currentFragmentgit
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(currentFragment);//  不是则隐藏
            currentFragment = fragment;  //  然后将传入的fragment赋值给currentFragment
            if (!fragment.isAdded()) { //  判断传入的fragment是否已经被add()过
                transaction.add(R.id.fragment, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }
}
