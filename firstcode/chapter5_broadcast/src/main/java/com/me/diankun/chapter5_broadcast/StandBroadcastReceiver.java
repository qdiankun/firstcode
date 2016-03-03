package com.me.diankun.chapter5_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by diankun on 2016/3/3.
 */
public class StandBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "StandBroadcastReceiver", Toast.LENGTH_SHORT).show();

        //优先接收到广播，阻断广播
        abortBroadcast();
    }
}
