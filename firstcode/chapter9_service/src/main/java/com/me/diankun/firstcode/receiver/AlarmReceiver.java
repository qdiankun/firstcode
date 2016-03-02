package com.me.diankun.firstcode.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.me.diankun.firstcode.service.LongTimeService;

/**
 * Created by diankun on 2016/3/1.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //接收到广播后，继续执行定时任务
        Intent i = new Intent(context, LongTimeService.class);
        context.startService(i);
    }
}
