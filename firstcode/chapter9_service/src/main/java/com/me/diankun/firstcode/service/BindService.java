package com.me.diankun.firstcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by diankun on 2016/3/1.
 */
public class BindService extends Service {

    private static final String TAG = BindService.class.getSimpleName();
    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        Log.d(TAG, "BindService ThreadId = " + Thread.currentThread().getId());
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    public class DownloadBinder extends Binder {

        public void startDownload() {
            Log.i(TAG, "startDownload");
        }

        public void getProgress() {
            Log.i(TAG, "getProgress");
        }

    }
}
