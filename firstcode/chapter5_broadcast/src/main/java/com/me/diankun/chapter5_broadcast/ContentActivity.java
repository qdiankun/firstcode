package com.me.diankun.chapter5_broadcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by diankun on 2016/3/3.
 */
public class ContentActivity extends Activity {


    private Button btn_force_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        ActivityCollector.addActivity(this);

        btn_force_line = (Button) findViewById(R.id.btn_force_line);
        btn_force_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///发送强制下线广播
                Intent i = new Intent("com.me.diankun.chapter5_broadcast.FORCELINE");
                sendBroadcast(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
