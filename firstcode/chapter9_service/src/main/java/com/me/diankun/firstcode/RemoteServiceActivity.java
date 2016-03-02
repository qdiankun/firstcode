package com.me.diankun.firstcode;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.me.diankun.firstcode.service.AIDLService;
import com.me.diankun.firstcode.service.ANRService;
import com.me.diankun.firstcode.service.MyAIDLService;
import com.me.diankun.firstcode.service.RemoteService;

/**
 * Created by diankun on 2016/3/1.
 */
public class RemoteServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start_service;
    private Button btn_start_remoteservice;
    private Button btn_start_bind_remoteservice;
    private Button btn_start_bind_aidl_remoteservice;

    private static final String TAG = RemoteServiceActivity.class.getSimpleName();

    //报错Service,Activity和Service运行在两个不同的进程
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RemoteService.DownloadBinder downloadBinder = (RemoteService.DownloadBinder) iBinder;
            downloadBinder.getProgress();
            downloadBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    //使用AIDL来绑定远程的Service
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //MyAIDLService.Stub.asInterface()方法将传入的IBinder对象传换成了MyAIDLService对象
            MyAIDLService aidlService = MyAIDLService.Stub.asInterface(iBinder);
            try {
                int result = aidlService.plus(5, 6);
                String str = aidlService.toUpperCase("welcome");
                Log.i(TAG, "result = " + result + "\t str = " + str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_service);

        Log.i(TAG, "onCreate  ThreadId = " + Thread.currentThread().getId() + "\t process id is ");

        initView();
    }


    private void initView() {
        btn_start_service = (Button) findViewById(R.id.btn_start_service);
        btn_start_remoteservice = (Button) findViewById(R.id.btn_start_remoteservice);
        btn_start_bind_remoteservice = (Button) findViewById(R.id.btn_start_bind_remoteservice);
        btn_start_bind_aidl_remoteservice = (Button) findViewById(R.id.btn_start_bind_aidl_remoteservice);

        btn_start_service.setOnClickListener(this);
        btn_start_remoteservice.setOnClickListener(this);
        btn_start_bind_remoteservice.setOnClickListener(this);
        btn_start_bind_aidl_remoteservice.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_start_service:
                Intent i = new Intent(RemoteServiceActivity.this, ANRService.class);
                startService(i);
                break;
            case R.id.btn_start_remoteservice:
                i = new Intent(RemoteServiceActivity.this, RemoteService.class);
                startService(i);
                break;
            case R.id.btn_start_bind_remoteservice:
                i = new Intent(RemoteServiceActivity.this, RemoteService.class);
                bindService(i, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_start_bind_aidl_remoteservice:
                //目前的跨进程通信其实并没有什么实质上的作用，因为这只是在一个Activity里调用了同一个应用程序的Service里的方法
                i = new Intent(RemoteServiceActivity.this, AIDLService.class);
                bindService(i, con, BIND_AUTO_CREATE);
                break;
        }
    }

}
