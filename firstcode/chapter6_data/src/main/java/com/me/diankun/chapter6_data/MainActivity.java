package com.me.diankun.chapter6_data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    private Context context = this;

    private final String[] items = {"保存数据到文件", "保存数据到SharedP中", "保存数据到DataBase中"};

    private final Class<?>[] classes = {SaveToFileActvity.class, SaveToSpActivity.class, SaveToDbActivity.class};
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
