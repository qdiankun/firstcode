package com.me.diankun.firstcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Blog介绍Service
 * Android Service完全解析，关于服务你所需知道的一切(上) http://blog.csdn.net/guolin_blog/article/details/11952435
 * Android Service完全解析，关于服务你所需知道的一切(下) http://blog.csdn.net/guolin_blog/article/details/9797169
 */
public class MainActivity extends AppCompatActivity {


    private Context context = this;

    private final String[] items = {"Start启动服务", "Bind启动服务", "启动IntentService",
            "前台服务", "查看服务器版本", "定时任务", "有关远程的Service"};

    private final Class<?>[] classes = {StartServiceActivity.class, BindServiceActivity.class, IntentServiceActivity.class,
            ForegroundServiceActivity.class, AsyncActivity.class, LongTimeActivity.class, RemoteServiceActivity.class};
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv = new ListView(context);
        mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);
        lv.setCacheColorHint(Color.TRANSPARENT);
        lv.setFadingEdgeLength(0);
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, classes[position]);
                startActivity(intent);
            }
        });
        setContentView(lv);
    }


}
