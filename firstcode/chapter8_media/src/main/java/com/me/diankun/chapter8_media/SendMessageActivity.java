package com.me.diankun.chapter8_media;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by diankun on 2016/3/8.
 */
public class SendMessageActivity extends AppCompatActivity {

    private EditText to;
    private EditText msgInput;

    private Button btn_send_message;

    private SendStatusReceiver mReceiver;
    private IntentFilter sendFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        mReceiver = new SendStatusReceiver();
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        //注册广播
        registerReceiver(mReceiver, sendFilter);

        to = (EditText) findViewById(R.id.to);
        msgInput = (EditText) findViewById(R.id.msg_input);
        btn_send_message = (Button) findViewById(R.id.btn_send_message);
        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), null, null);
                //sendSms(SendMessageActivity.this, "10086", "查话费");
                sendMessage();
            }
        });

    }

    /**
     * 发送短信
     *
     * @param context
     * @param phoneNumber
     * @param content
     */
    public static void sendSms(Context context, String phoneNumber,
                               String content) {
        Uri uri = Uri.parse("smsto:"
                + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }


    public void sendMessage() {
        SmsManager smsManager = SmsManager.getDefault();
        Intent sentIntent = new Intent("SENT_SMS_ACTION");
        PendingIntent pi = PendingIntent.getBroadcast(SendMessageActivity.this, 0, sentIntent, 0);
        smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), pi, null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    class SendStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                // 短信发送成功
                Toast.makeText(context, "Send succeeded", Toast.LENGTH_LONG).show();
            } else {
                // 短信发送失败
                Toast.makeText(context, "Send failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
