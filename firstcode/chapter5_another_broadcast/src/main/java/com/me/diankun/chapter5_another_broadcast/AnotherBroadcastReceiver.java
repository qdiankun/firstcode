package com.me.diankun.chapter5_another_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by diankun on 2016/3/3.
 */
public class AnotherBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "This is Another BroadCast", Toast.LENGTH_SHORT).show();
    }

}
