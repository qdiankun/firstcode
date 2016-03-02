package com.me.diankun.firstcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.me.diankun.firstcode.service.LongTimeService;

/**
 * Created by diankun on 2016/3/1.
 */
public class LongTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start_longtime, btn_stop_longtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_time);

        initView();
    }

    private void initView() {
        btn_start_longtime = (Button) findViewById(R.id.btn_start_longtime);
        btn_stop_longtime = (Button) findViewById(R.id.btn_stop_longtime);

        btn_start_longtime.setOnClickListener(this);
        btn_stop_longtime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(LongTimeActivity.this, LongTimeService.class);
        switch (view.getId()) {
            case R.id.btn_start_longtime:
                startService(i);
                break;
            case R.id.btn_stop_longtime:
                stopService(i);
                break;
        }
    }
}
