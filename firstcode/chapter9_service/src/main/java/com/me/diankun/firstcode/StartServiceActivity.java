package com.me.diankun.firstcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.me.diankun.firstcode.service.MyService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartServiceActivity extends AppCompatActivity {

    private static final String TAG = StartServiceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ActivityThreadId = " + Thread.currentThread().getId());
        setContentView(R.layout.activity_start_service);
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.btn_start_service)
    void startService(View view) {
        Intent startIntent = new Intent(StartServiceActivity.this, MyService.class);
        startService(startIntent);
    }

    @OnClick(R.id.btn_start_service_again)
    void startServiceAgain(View view) {
        Intent startIntent = new Intent(StartServiceActivity.this, MyService.class);
        startService(startIntent);
    }

    @OnClick(R.id.btn_stop_service)
    void stopService(View view) {
        Intent stopIntent = new Intent(StartServiceActivity.this, MyService.class);
        stopService(stopIntent);
    }


}
