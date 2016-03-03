package com.me.diankun.chapter5_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 发送标准的自定义广播
 * Created by diankun on 2016/3/3.
 */
public class SendStandBroadActivity extends AppCompatActivity {

    private Button btn_send_standbroadcast;
    private Button btn_send_orderbroadcast;
    private Button btn_send_localbroadcast;

    private LocalReceiver localRecevier;
    private LocalBroadcastManager localBroadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_broadcast);

        btn_send_standbroadcast = (Button) findViewById(R.id.btn_send_standbroadcast);
        btn_send_orderbroadcast = (Button) findViewById(R.id.btn_send_orderbroadcast);
        btn_send_localbroadcast = (Button) findViewById(R.id.btn_send_localbroadcast);
        btn_send_standbroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///com.me.diankun.chapter5_broadcast.BROADCAST  是自定义广播
                Intent i = new Intent("com.me.diankun.chapter5_broadcast.BROADCAST");
                sendBroadcast(i);
            }
        });


        //发送有序广播
        btn_send_orderbroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送有序广播，通过设置receiver中的 android:priority 设置优先级 ，越大越先接收到
                Intent i = new Intent("com.me.diankun.chapter5_broadcast.BROADCAST");
                sendOrderedBroadcast(i, null);
            }
        });

        ///////////////////////////////////////
        /**
         * 本地广播注意点：
         * 1.本地广播无法通过静态注册方式接收
         *
         * 可因此不需要以明确地知道正在发送的广播不会离开我们的程序，担心机密数据泄漏的问题
         * 其他的程序无法将广播发送到我们程序的内部，因此不需要担心会有安全漏洞的隐患
         * 发送本地广播比起发送系统全局广播将会更加高效
         */


        //发送本地广播
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        btn_send_localbroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("com.me.diankun.chapter5_broadcast.BROAD_LOCAL");
                localBroadcastManager.sendBroadcast(i);//发送本地广播
            }
        });

        //动态注册本地广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.me.diankun.chapter5_broadcast.BROAD_LOCAL");
        localRecevier = new LocalReceiver();
        localBroadcastManager.registerReceiver(localRecevier, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localRecevier);
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Local BroadCast ", Toast.LENGTH_SHORT).show();
        }
    }

}
