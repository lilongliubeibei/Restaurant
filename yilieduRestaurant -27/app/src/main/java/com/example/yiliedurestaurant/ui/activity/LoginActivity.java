package com.example.yiliedurestaurant.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.base.ActivityBase;
import com.example.yiliedurestaurant.presenter.LoginPresenter;

public class LoginActivity extends ActivityBase<LoginPresenter> {
    private EditText edit_username;
    private EditText edit_password;
    private Button login;
    private TextView register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        prensenterBase =new LoginPresenter();
        prensenterBase.setActivityBase(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("登录");
        initViews();
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        if (userName != null) {
            edit_username.setText(userName);
            edit_username.setSelection(userName.length());
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_username.getText().toString();
                String pass = edit_password.getText().toString();
                if (name.equals("") || pass.equals("")) {
                    toast("账号或密码为空",LoginActivity.this);
                    return;
                }
                prensenterBase.login(name, pass);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initViews() {
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password = (EditText) findViewById(R.id.edit_password);
        login = (Button) findViewById(R.id.bt_login);
        register = (TextView) findViewById(R.id.tv_register);
    }

    private void saveLoginStatus(boolean status, String userName) {
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("loginInfo", 0);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.apply();
    }

    public void LogingSuccess() {
        toast("登录成功",this);
        saveLoginStatus(true, edit_username.getText().toString());
        Intent intent = new Intent();
        setResult(222, intent);
        finish();
    }
}