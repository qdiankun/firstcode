package com.me.diankun.firstcode;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.me.diankun.firstcode.service.BindService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindServiceActivity extends AppCompatActivity {


    private static final String TAG = BindServiceActivity.class.getSimpleName();

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BindService.DownloadBinder downloadBinder = (BindService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BindServiceActivity ThreadId = " + Thread.currentThread().getId());
        setContentView(R.layout.activity_bind_service);
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_startbind_service)
    void startBindService() {
        Intent intent = new Intent(BindServiceActivity.this, BindService.class);
        // BIND_AUTO_CREATE表示活动和服务进行绑定后自动创建服务，会使BindService中的onCreate()方法得到执行，但onStartCommand()方法不会执行
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_stopbind_service)
    void stopBindService() {
        unbindService(connection);
    }


}
