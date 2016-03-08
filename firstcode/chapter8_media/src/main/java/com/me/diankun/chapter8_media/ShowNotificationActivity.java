package com.me.diankun.chapter8_media;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by diankun on 2016/3/8.
 */
public class ShowNotificationActivity extends AppCompatActivity {

    private Button btn_show_notification;
    private Button btn_dismiss_notification;
    //大图标
    private Bitmap largeBitmap;
    //Notification
    private Notification mNotification;
    private static final int NOTIFYID_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        //创建大图标的Bitmap
        largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iv_lc_icon);

        btn_show_notification = (Button) findViewById(R.id.btn_show_notification);
        btn_dismiss_notification = (Button) findViewById(R.id.btn_dismiss_notification);
        btn_show_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
        btn_dismiss_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissNotification();
            }
        });

    }

    private void dismissNotification() {
        //除了可以根据ID来取消Notification外,还可以调用cancelAll();关闭该应用产生的所有通知
        //取消Notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFYID_1);
    }

    private void showNotification() {
        //1.获取NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //2.创建通知栏Builder类
        Notification.Builder builder = new Notification.Builder(this);
        //定义一个PendingIntent点击Notification后启动一个Activity
        Intent i = new Intent(ShowNotificationActivity.this, OtherActivity.class);
        PendingIntent pit = PendingIntent.getActivity(ShowNotificationActivity.this, 0, i, 0);
        //3.使用builder为notification赋值
        builder.setContentTitle("叶良辰")                            //标题
                .setContentText("我有一百种方法让你呆不下去~")       //内容
                .setSubText("——记住我叫叶良辰")                        //内容下面的一小段文字
                .setTicker("收到叶良辰发送过来的信息~")                  //收到信息后状态栏显示的文字信
                .setWhen(System.currentTimeMillis())                 //设置通知时间
                .setSmallIcon(R.mipmap.ic_lol_icon)                 //设置通知时间
                .setLargeIcon(largeBitmap)                           //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND)//设置默认的三色灯与振动器
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))
                .setAutoCancel(true)                                //设置点击后取消Notification
                .setContentIntent(pit);                             //设置PendingIntent
        //4.调用builder.build()方法
        mNotification = builder.build();
        //5.调用notify()发送通知
        notificationManager.notify(NOTIFYID_1, mNotification);
    }
}
