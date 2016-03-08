package com.me.diankun.chapter8_media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.widget.TextView;

/**
 * 系统发出的短信广播正是一条有序广播
 * <p/>
 * Created by diankun on 2016/3/8.
 */
public class ReceiverMessageActivity extends AppCompatActivity {


    private TextView sender;
    private TextView content;


    private IntentFilter receiveFilter;
    private MessageReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_message);

        sender = (TextView) findViewById(R.id.sender);
        content = (TextView) findViewById(R.id.content);

        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        //优先接收短信
        //receiveFilter.setPriority(100);
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }

    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus"); // 提取短信消息
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            String address = messages[0].getOriginatingAddress(); // 获取发送方号码

            String fullMessage = "";
            for (SmsMessage message : messages) {
                //Log.i("TAG", "Body = " + message.getMessageBody());
                fullMessage += message.getMessageBody(); // 获取短信内容
            }
            sender.setText(address);
            content.setText(fullMessage);

            //停止发送短信广播
            //abortBroadcast();
        }
    }
}
