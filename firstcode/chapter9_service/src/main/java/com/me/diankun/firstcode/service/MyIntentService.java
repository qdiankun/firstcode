package com.me.diankun.firstcode.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * IntentService,在onHandleIntent()方法中自动开启一个线程处理耗时操作，操作结束后，自动调用stopSelf()
 * <p/>
 * 注意：无参的构造函数调用有参的父类构造函数
 */
public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();


    public MyIntentService() {
        // 调用父类的有参构造函数
        super("com.me.diankun.firstcode.service.MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "MyIntentService ThreadId = " + Thread.currentThread().getId());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
