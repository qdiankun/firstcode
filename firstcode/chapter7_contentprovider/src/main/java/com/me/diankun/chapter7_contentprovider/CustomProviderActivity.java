package com.me.diankun.chapter7_contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by diankun on 2016/3/4.
 */
public class CustomProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_provider_add;
    private Button btn_provider_query;
    private Button btn_provider_update;
    private Button btn_provider_delete;

    private static final String TAG = CustomProviderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_provider);

        btn_provider_add = (Button) findViewById(R.id.btn_provider_add);
        btn_provider_query = (Button) findViewById(R.id.btn_provider_query);
        btn_provider_update = (Button) findViewById(R.id.btn_provider_update);
        btn_provider_delete = (Button) findViewById(R.id.btn_provider_delete);

        btn_provider_add.setOnClickListener(this);
        btn_provider_query.setOnClickListener(this);
        btn_provider_update.setOnClickListener(this);
        btn_provider_delete.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_provider_add:
                providerAddBook();
                break;
            case R.id.btn_provider_delete:
                providerDeleteBook();
                break;
            case R.id.btn_provider_query:
                providerQueryBook();
                break;
            case R.id.btn_provider_update:
                providerUpdateBook();
                break;
            default:
                break;
        }
    }

    /**
     * 更新数据
     */
    private void providerUpdateBook() {
        Uri uri = Uri.parse("content://com.me.diankun.chapter6_data/book/1");
        ContentValues values = new ContentValues();
        values.put("name","first code");
        values.put("price",56.0f);
        int updateNumber = getContentResolver().update(uri, values, null, null);
        Log.i(TAG, "\t updateNumber = " + updateNumber);
    }

    /**
     * 查询数据
     */
    private void providerQueryBook() {
        Uri uri = Uri.parse("content://com.me.diankun.chapter6_data/book/1");
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    float price = cursor.getFloat(cursor.getColumnIndex("price"));
                    Log.i(TAG, "\t name = " + name + "\t author = " + author + "\t pages = " + pages + "\t price = " + price);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    /**
     * 删除数据
     */
    private void providerDeleteBook() {
        Uri uri = Uri.parse("content://com.me.diankun.chapter6_data/book/1");
        int deleteNumber = getContentResolver().delete(uri, null, null);
        Log.i(TAG, "providerDeleteBook deleteNumber = " + deleteNumber);
    }

    /**
     * 添加数据
     */
    private void providerAddBook() {
        Uri uri = Uri.parse("content://com.me.diankun.chapter6_data/book");
        ContentValues values = new ContentValues();
        values.put("name", "A Clash of Kings");
        values.put("author", "George Martin");
        values.put("pages", 1040);
        values.put("price", 22.85);
        Uri insertUri = getContentResolver().insert(uri, values);
         String number = insertUri.getPathSegments().get(1);
        Log.i(TAG, "providerAddBook number = " + number);
    }
}
