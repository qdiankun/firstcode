package com.me.diankun.firstcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 实现AIDL的方式:
 *
 * 实现跨进程通信，在一个进程访问到了另外一个进程中的方法，目前并没有什么作用，只是在一个Activity里调用了同一个应用程序里的Service方法，
 * 真正的意义让一个应用程序访问另一个应用程序中的Service,实现共享Service中的功能，在另外一个应用程序中调用
 * 		Intent intent = new Intent(ServiceActivity.this, RemoteService2.class);
 *		bindService(intent, connection3, BIND_AUTO_CREATE);
 *	但是另外的应用程序中根本没有RemoteService2这个类，
 *	注意事项：
     1. 因此必须使用隐式Intent,给Service设置Action
     <service
         android:name=".service.RemoteService2"
         android:process=":remote" >
        <intent-filter >
            <action android:name="com.diankun.interview.MYAIDLService"/>
        </intent-filter>
     </service>
     此时：就没有必要将此Service设置为:remote，因为对于另外一个应用程序，这个Service就是另外一个进程了
    2. 将MyAIDLService.aidl拷贝到要调用Service的应用的包中，存放的包名要与其原先所在的包名相同
    3. 调用e.g
     Intent intent = new Intent("com.diankun.interview.MYAIDLService");
     //必须指定应用名称
     intent.setPackage("com.diankun.interview");
     bindService(intent, conn, BIND_AUTO_CREATE);
 * Created by diankun on 2016/3/1.
 */
public class AIDLService2 extends Service {

    MyAIDLService.Stub mBinder = new MyAIDLService.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            return str.toUpperCase();
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
