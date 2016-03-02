package com.me.diankun.firstcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * ANRService暂时解决方法:
 * 在 AndroidManifest中修改为 android:process=":remote",将其设置成远程Service,等待30秒打印出信息
 * ,不会再报ANR, 原因：设置远程后，Service已经在另外一个进程中的主线程中运行，只会阻塞该进程中的主线程
 * <p/>
 * 问题一：  为什么点击Start Service按钮程序就不会崩溃，而点击Bind Service按钮就会崩溃呢？
 * bindService()让我们的Activity与Service建立关联， 但此时的Service已经是一个远程的Service,Activity与此Service运行在不同的进程中，就不能使用传统的绑定方式
 * <p/>
 * 那么如何才能让Activity与一个远程Service建立关联呢？这就要使用AIDL来进行跨进程通信了（IPC）
 * AIDL（Android Interface Definition Language）是Android接口定义语言的意思，它可以用于让某个Service与多个应用程序组件之间进行跨进程通信，
 * 从而可以实现多个应用程序共享同一个Service的功能
 *
 * Created by diankun on 2016/3/1.
 */
public class RemoteService extends Service {

    private static final String TAG = RemoteService.class.getSimpleName();
    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate ThreadId = " + Thread.currentThread().getId());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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
