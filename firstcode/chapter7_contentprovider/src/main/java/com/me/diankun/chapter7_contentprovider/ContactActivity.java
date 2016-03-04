package com.me.diankun.chapter7_contentprovider;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ContactResolver来获取电话信息
 * <p/>
 * Created by diankun on 2016/3/4.
 */
public class ContactActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<String>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Log.i("TAG", "onCreate");

        readContacts();

        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mDatas);

        mListView.setAdapter(mAdapter);
    }

    private void readContacts() {

        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String displayNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mDatas.add(displayName + " : " + displayNumber);
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


}
