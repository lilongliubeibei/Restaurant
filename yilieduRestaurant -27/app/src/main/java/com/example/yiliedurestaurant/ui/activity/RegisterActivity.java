package com.example.yiliedurestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.base.ActivityBase;
import com.example.yiliedurestaurant.model.Bean_UserDetail;
import com.example.yiliedurestaurant.presenter.RegisterPresenter;


public class RegisterActivity extends ActivityBase<RegisterPresenter>  {
    private EditText editText_username;
    private EditText editText_password;
    private EditText editText_passwordAgain;
    private Button register;
    String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        prensenterBase =new RegisterPresenter();
        prensenterBase.setActivityBase(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("艺立教育餐厅");
        initViews();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText_username.getText().toString().trim();
                String password = editText_password.getText().toString().trim();
                String passwordAgain = editText_passwordAgain.getText().toString().trim();
                if (username.equals("") || password.equals("") || passwordAgain.equals("")) {
                    toast("账号或密码为空",RegisterActivity.this);
                    return;
                }
                if (!password.equals(passwordAgain)) {
                    toast("密码不一致",RegisterActivity.this);
                    return;
                }
                prensenterBase.register(username, password);
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
        editText_username = (EditText) findViewById(R.id.username);
        editText_password = (EditText) findViewById(R.id.password);
        editText_passwordAgain = (EditText) findViewById(R.id.password_again);
        register = (Button) findViewById(R.id.register);
    }


    public void registerSuccess(Bean_UserDetail bean_userDetail) {
        toast("注册成功",this);
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("userName", RegisterActivity.this.username);
        startActivity(intent);
        finish();
    }
}
