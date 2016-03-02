package com.me.diankun.aidlserver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.me.diankun.firstcode.service.MyAIDLService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btn_access_aidlservice;

    //访问其他进程中的Service
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyAIDLService service = MyAIDLService.Stub.asInterface(iBinder);
            try {
                int result = service.plus(5, 10);
                String str = service.toUpperCase("welcome to use aidl");
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
        setContentView(R.layout.activity_main);

        LayoutInflater.from(this);

        btn_access_aidlservice = (Button) findViewById(R.id.btn_access_aidlservice);
        btn_access_aidlservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("com.me.diankun.aidlservice.service.AIDLService2");
                //指定package
                i.setPackage("com.me.diankun.firstcode");
                bindService(i, conn, BIND_AUTO_CREATE);
            }
        });
    }
}
