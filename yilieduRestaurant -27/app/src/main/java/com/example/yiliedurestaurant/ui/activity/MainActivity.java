package com.example.yiliedurestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.yiliedurestaurant.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager
    Button button;
    TextView textView;
    private List<View> viewList;//view数组
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                startActivity(new Intent(MainActivity.this, MyActivity.class));
                MainActivity.this.finish();

            }
        });
//    延时跳转
        countDownTimer = new MyCountDownTimer(this, 10000, 1000);
        countDownTimer.start();
// 将要分页显示的View装入数组中
        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        textView.setText("1/3");
                        break;
                    case 1:
                        textView.setText("2/3");
                        break;
                    case 2:
                        textView.setText("3/3");
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initview() {
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2, null);
        view3 = inflater.inflate(R.layout.layout3, null);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }


    static class MyCountDownTimer extends CountDownTimer {
        WeakReference<MainActivity> weakReference;

        public MyCountDownTimer(MainActivity mainActivity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.weakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            MainActivity mainActivity = weakReference.get();
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            mainActivity.button.setText("跳过" + value + "s");
        }

        @Override
        public void onFinish() {
            MainActivity mainActivity = weakReference.get();
            mainActivity.startActivity(new Intent(mainActivity, MyActivity.class));
            mainActivity.finish();
        }
    }

    ;

    class MyPagerAdapter extends PagerAdapter {
        List<View> viewList;

        public MyPagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(viewList.get(position));


            return viewList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }
}

