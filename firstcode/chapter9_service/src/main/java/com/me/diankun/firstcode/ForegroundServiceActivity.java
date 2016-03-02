package com.me.diankun.firstcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.me.diankun.firstcode.service.ForegroundService;

public class ForegroundServiceActivity extends AppCompatActivity {

    private static final String TAG = ForegroundServiceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service);
        Log.i(TAG, "onCreate");
        Log.i(TAG, "IntentServiceActivity ThreadId = " + Thread.currentThread().getId());

        findViewById(R.id.btn_startforground_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ForegroundServiceActivity.this, ForegroundService.class);
                startService(i);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
