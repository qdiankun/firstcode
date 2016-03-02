package com.me.diankun.firstcode.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.me.diankun.firstcode.receiver.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diankun on 2016/3/1.
 */
public class LongTimeService extends Service {

    private static final String TAG = LongTimeService.class.getSimpleName();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand");
        //打印Log
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Execute at " + simpleDateFormat.format(new Date()));
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int oneMinute = 6 * 1000; // 一分钟
        long triggerAtMillis = SystemClock.elapsedRealtime() + oneMinute;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
