package com.me.diankun.chapter6_data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/2.
 */
public class SaveToDbActivity extends AppCompatActivity {


    private MyDatabaseHelper databaseHelper;
    private static final String TAG = SaveToDbActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveto_db);
        ButterKnife.bind(this);

        databaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
    }

    @OnClick(R.id.btn_create_sqlite)
    void createSqlite(View view) {
        // 检测当前程序中无BookStore.db这个数据库，会创建数据库并调用onCreate中的方法
        // 再次点击时，会发现已经存在，不会再次去创建
        databaseHelper.getWritableDatabase();
    }

    @OnClick(R.id.btn_update_sqlite)
    void updateSqlite(View view) {
        // 修改版本号为2，执行MyDatabaseHelper中的onUpgrade方法
        databaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        databaseHelper.getWritableDatabase();
    }


    ////////////////////////////////////////////////////////
    ////////////////////////////使用ContentValue语句执行CRUD操作
    ////////////////////////////////////////////////////////

    @OnClick(R.id.btn_add_book_data)
    void addDataToBook(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // 组装一条数据
        values.put("name", "The Da Vinci Code");
        values.put("author", "Dan Brown");
        values.put("pages", 454);
        values.put("price", 16.96);
        db.insert("Book", null, values);
        // 组装第二条数据
        values.clear();
        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 514);
        values.put("price", 19.95);
        db.insert("Book", null, values);
    }


    @OnClick(R.id.btn_update_book_data)
    void updateBookData(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", 10.99);
        // ？表示的是占位符
        db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
    }

    @OnClick(R.id.btn_delete_book_data)
    void deleteBookData(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("Book", "pages>?", new String[]{"500"});
    }

    @OnClick(R.id.btn_query_book_data)
    void queryBookData(View view) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        /**
         * 第一参数:	表名
         * 第二个参数：	查询列名	String[]  ,null 查询全部的列
         * 第三个参数：	指定Where的约束条件含占位符?
         * 第四个参数: 	为占位符提供具体的值
         * 第五个参数:	指定需要GroupBy的值
         * 第六个参数:	对GroupBy的结果进一步约束
         * 第七个参数：	对查询结果的排序方式
         */
        try {
            cursor = db.query("Book", null, null, null, null, null, null);
            //遍历Cursor,取出数据
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int page = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getInt(cursor.getColumnIndex("price"));
                Log.i(TAG, "name:" + name + " ,price:" + price + " ,page:" + page + ",price:" + price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    ////////////////////////////////////////////////////////
    ////////////////////////////使用SQL语句执行CRUD操作
    ////////////////////////////////////////////////////////

    @OnClick(R.id.btn_add_book_data_sql)
    void addDataToBookSql(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)", new String[]{"The Da Vinci Code", "Dan Brown", "454", "16.96"});
        db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)", new String[]{"The Lost Symbol", "Dan Brown", "510", "19.95"});
    }


    @OnClick(R.id.btn_update_book_data_sql)
    void updateBookDataSql(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("update Book set price = ? where name = ? ", new String[]{"10.98", "The Da Vinci Code"});
    }

    @OnClick(R.id.btn_delete_book_data_sql)
    void deleteBookDataSql(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("delete from Book where pages > ? ", new String[]{"500"});
    }

    @OnClick(R.id.btn_query_book_data_sql)
    void queryBookDataSql(View view) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from Book", null);
            //遍历Cursor,取出数据
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int page = cursor.getInt(cursor.getColumnIndex("pages"));
                    double price = cursor.getInt(cursor.getColumnIndex("price"));
                    Log.i(TAG, "name:" + name + " ,author:" + author + " ,price:" + price + " ,page:" + page + ",price:" + price);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    @OnClick(R.id.btn_replace_book)
    void useTransaction() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        try {
            //开启事务管理
            db.beginTransaction();
            //删除数据
            db.delete("Book", null, null);

            //模拟插入过程中报错
//			if(true){
//				throw new NullPointerException();
//			}

            //插入数据
            ContentValues values = new ContentValues();
            values.put("name", "HeadFirst Desin Pattern");
            values.put("author", "headfirst");
            values.put("pages", 600);
            values.put("price", 26.23);
            db.insert("Book", null, values);
            //事务执行成功
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭事务管理
            db.endTransaction();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
