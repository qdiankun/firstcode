package com.me.diankun.chapter6_data;

import android.app.Application;

/**
 * Created by diankun on 2016/3/2.
 */
public class DataApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.register(this);
    }
}
