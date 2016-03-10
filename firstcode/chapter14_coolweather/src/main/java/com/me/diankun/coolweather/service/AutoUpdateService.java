package com.me.diankun.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.me.diankun.coolweather.receiver.AutoUpdateReceiver;
import com.me.diankun.coolweather.utils.HttpCallbackListener;
import com.me.diankun.coolweather.utils.HttpUtil;
import com.me.diankun.coolweather.utils.Utility;

/**
 * Created by diankun on 2016/3/10.
 */
public class AutoUpdateService extends Service
{

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                updateWeather();
            }
        }).start();
        //设置定时任务，每8个小时执行一次
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 *60 * 1000;//8小时
        //int anHour = 10 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this,AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String countryCode = sp.getString("country_code", "");
        String address = "http://www.weather.com.cn/data/cityinfo/"
                + countryCode + ".html";
        Log.i("dk", "AutoUpdateService #upateWeather url = " + address);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

            @Override
            public void onFinish(String response) {
                Utility.handleWeatherResponse(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}