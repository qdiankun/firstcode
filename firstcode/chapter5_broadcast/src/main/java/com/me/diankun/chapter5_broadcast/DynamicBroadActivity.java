package com.me.diankun.chapter5_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by diankun on 2016/3/3.
 */
public class DynamicBroadActivity extends AppCompatActivity {

    private NetBroadCastReceiver mReceiver;
    private static final String TAG = DynamicBroadActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_broadcast);

        Log.i(TAG, "onCreate");
        mReceiver = new NetBroadCastReceiver();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        unregisterReceiver(mReceiver);
    }

    private class NetBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            Log.i(TAG, "networkInfo.isAvailable() = " + networkInfo.isAvailable());
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(DynamicBroadActivity.this, "网络连接", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DynamicBroadActivity.this, "网络断开", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
