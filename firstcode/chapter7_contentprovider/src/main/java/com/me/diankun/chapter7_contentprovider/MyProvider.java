package com.me.diankun.chapter7_contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * 内容提供器的使用模式
 * <p/>
 * Created by diankun on 2016/3/4.
 */
public class MyProvider extends ContentProvider {

    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;

    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;


    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.diankun.interview", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.diankun.interview", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.diankun.interview", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.diankun.interview", "table2/#", TABLE2_ITEM);
    }


    /**
     * content://com.example.app.provider/table1/1
     *
     * 这就表示调用方期望访问的是com.example.app这个应用的table1表中id为1的数据。
     *
     * 内容URI的格式主要就只有以上两种，以路径结尾就表示期望访问该表中所有的数据，以id结尾就表示期望访问该表中拥有相应id的数据
     *
     * 我们可以使用通配符的方式来分别匹配这两种格式的内容URI
     *
     *1. *：表示匹配任意长度的任意字符
     2. #：表示匹配任意长度的数字
     所以，一个能够匹配任意表的内容URI格式就可以写成：
     content://com.example.app.provider/*
     而一个能够匹配table1表中任意一行数据的内容URI格式就可以写成：
     content://com.example.app.provider/table1/#
     *
     */


    /**
     * 初始化内容提供器的时候调用。通常会在这里完成对数据库的创建和升级等操作
     * 返回true表示内容提供器初始化成功，返回false则表示失败
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        return false;
    }

    /**
     * 从内容提供器中查询数据  查询的结果存放在Cursor对象中返回
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:// 查询Table1的所有数据

                break;
            case TABLE1_ITEM:// 查询Table1的单条数据

                break;
            case TABLE2_DIR:// 查询Table2的所有数据

                break;
            case TABLE2_ITEM:// 查询Table2的单条数据

                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 根据传入的内容URI来返回相应的MIME类型
     * <p/>
     * <p/>
     * 一个内容URI所对应的MIME字符串主要由三部分组分，Android对这三个部分做了如下格式规定
     * <p/>
     * 1. 必须以vnd开头。
     * 2. 如果内容URI以路径结尾，则后接android.cursor.dir/，如果内容URI以id结尾，则后接android.cursor.item/。
     * 3. 最后接上vnd.<authority>.<path>。
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.diankun.interview.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.diankun.interview.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.diankun.interview.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.diankun.interview.table2";
            default:
                break;
        }
        return null;
    }

    /**
     * 向内容提供器中添加一条数据。使用uri参数来确定要添加到的表，
     * 待添加的数据保存在values参数中。添加完成后，返回一个用于表示这条新记录的URI。
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }


    /**
     * 从内容提供器中删除数据。使用uri参数来确定删除哪一张表中的数据，selection,和selectionArgs参数用于约束删除哪些行，被删除的行数将作为返回值返
     */
    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    /**
     * 更新内容提供器中已有的数据。使用uri参数来确定更新哪一张表中的数据，新数据保存在values参数中，selection和selectionArgs参数用于约束更新哪些行，受影响的行数将作为返回值返回。
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
