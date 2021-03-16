package com.ss.android.ugc.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ss.android.ugc.demo.widget.Clock;

import androidx.annotation.HalfFloat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ClockActivity extends AppCompatActivity {

    private View mRootView;
    private Clock mClockView;
    private Handler mHandler;
    private Runnable runnable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mRootView = findViewById(R.id.root);
        mClockView = findViewById(R.id.clock);
        mHandler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                mClockView.invalidate();
                mHandler.postDelayed(this,1000);
            }
        };
        mHandler.postDelayed(runnable,1000);
    }
    @Override
    protected void onDestroy(){
        mHandler.removeCallbacks(runnable);
        super.onDestroy();
        getDelegate().onDestroy();
    }
}
