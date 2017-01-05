package com.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import doctor.com.CircleProgress;

public class MainActivity extends Activity implements View.OnClickListener {

    final int[] mColors = new int[]{
            Color.parseColor("#008B00"),
            Color.parseColor("#008B8B"),
            Color.parseColor("#3B3B3B"),
            Color.parseColor("#3A5FCD"),
            Color.parseColor("#4876FF"),
            Color.parseColor("#836FFF"),
    };
    CircleProgress mCircleProgress1;
    CircleProgress mCircleProgress2;
    CircleProgress mCircleProgress3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleProgress1 = (CircleProgress) findViewById(R.id.circleProgress1);
        mCircleProgress2 = (CircleProgress) findViewById(R.id.circleProgress2);
        mCircleProgress3 = (CircleProgress) findViewById(R.id.circleProgress3);
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_hide).setOnClickListener(this);
        findViewById(R.id.btn_setColors).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                mCircleProgress3.show();
                break;
            case R.id.btn_hide:
                mCircleProgress3.hide();
                break;
            case R.id.btn_setColors:
                mCircleProgress1.setColorSchemeColors(mColors);
                mCircleProgress2.setColorSchemeColors(mColors);
                mCircleProgress3.setColorSchemeColors(mColors);
                break;
        }
    }
}
