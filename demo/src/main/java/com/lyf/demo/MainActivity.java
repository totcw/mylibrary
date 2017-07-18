package com.lyf.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kankan.wheel.widget.WheelDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WheelDialog dialog = new WheelDialog(this);
        dialog.setViewDistrictShow(false);
        dialog.show();
    }
}
