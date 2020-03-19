package com.example.yiliedurestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.model.Bean_FoodDetails;
import com.example.yiliedurestaurant.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FoodDetailsActivity extends AppCompatActivity {
    ImageView iv_food;
    TextView tv_foodname;
    TextView tv_fooddetails;
    TextView tv_foodprice;
    Bean_FoodDetails foodDetails;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("食物详情");
        Intent intent = getIntent();
        int foodId = intent.getIntExtra("foodId", 0);
        lodFooddetils(foodId);
        iv_food = findViewById(R.id.iv_food);
        tv_foodname = findViewById(R.id.tv_foodname);
        tv_fooddetails = findViewById(R.id.tv_fooddetails);
        tv_foodprice = findViewById(R.id.tv_foodprice);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Glide.with(FoodDetailsActivity.this).load(foodDetails.getFood().getImgUrl()).into(iv_food);
                        tv_foodname.setText(foodDetails.getFood().getName());
                        tv_fooddetails.setText(foodDetails.getFood().getDes());
                        tv_foodprice.setText(String.valueOf(foodDetails.getFood().getPrice())+"元/份");
                        break;
                }
            }
        };

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

    private void lodFooddetils(int foodId) {
        String url = "https://yiliedu.cn/app/food?foodId=" + foodId;
        HttpUtil.OkHttpRequestGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(FoodDetailsActivity.this, "获取详情失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsedata = response.body().string();
                Gson gson = new Gson();
               foodDetails = gson.fromJson(responsedata, Bean_FoodDetails.class);
               
                Message message = Message.obtain();
                message.what = 1;
                handler.sendEmptyMessage(1);

            }

        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
