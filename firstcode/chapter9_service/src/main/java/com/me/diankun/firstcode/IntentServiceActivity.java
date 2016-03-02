package com.me.diankun.firstcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.me.diankun.firstcode.service.MyIntentService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntentServiceActivity extends AppCompatActivity {

    private static final String TAG = IntentServiceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ButterKnife.bind(this);
        Log.i(TAG, "onCreate");
        Log.i(TAG, "IntentServiceActivity ThreadId = " + Thread.currentThread().getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_startintent_service)
    void satartIntentService() {
        Intent i = new Intent(IntentServiceActivity.this, MyIntentService.class);
        startService(i);
    }


}
