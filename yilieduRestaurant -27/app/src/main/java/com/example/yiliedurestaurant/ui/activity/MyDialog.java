package com.example.yiliedurestaurant.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.yiliedurestaurant.Interface.MyDialogInterface;
import com.example.yiliedurestaurant.R;

public class MyDialog extends Dialog implements View.OnClickListener {
    EditText editText_money;
    Button button_true;
    MyDialogInterface myDialogInterface;

    public void setMyDialogInterface(MyDialogInterface myDialogInterface) {
        this.myDialogInterface = myDialogInterface;
    }

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.mydialog);
        button_true = findViewById(R.id.bt_true);
        editText_money = findViewById(R.id.edit_money);
        button_true.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_true:
                myDialogInterface.addmoney(Integer.parseInt(editText_money.getText().toString()));
                dismiss();
                break;
        }
    }
}

