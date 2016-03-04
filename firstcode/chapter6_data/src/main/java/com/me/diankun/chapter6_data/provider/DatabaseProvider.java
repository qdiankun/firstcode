package com.me.diankun.chapter6_data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.me.diankun.chapter6_data.MyDatabaseHelper;

/**
 *
 * 内容URI给内容提供器中的数据建立了唯一标识符，它主要由两部分组成，权限（authority）和路径（path）。
 *      权限是用于对不同的应用程序做区分的，一般为了避免冲突，都会采用程序包名的方式来进行命名。比如某个程序的包名是com.example.app，
 *      那么该程序对应的权限就可以命名为com.example.app. provider。
 *
 *      路径则是用于对同一应用程序中不同的表做区分的，通常都会添加到权限的后面。
 *      比如某个程序的数据库里存在两张表，table1和table2，这时就可以将路径分别命名为/table1和/table2，
 *      然后把权限和路径进行组合，内容URI就变成了com.example.app.provider/table1和com.example.app.provider/table2。
 *      不过，目前还很难辨认出这两个字符串就是两个内容URI，我们还需要在字符串的头部加上协议声明。因此，内容URI最标准的格式写法如下：


        content://com.example.app.provider/table1
        content://com.example.app.provider/table2
 *
 *
 * Created by diankun on 2016/3/4.
 */
public class DatabaseProvider extends ContentProvider {

    private MyDatabaseHelper databaseHelper;

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;

    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    //provider节点下的authorities值，就是权限
    public static final String AUTHORITY = "com.me.diankun.chapter6_data";


    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        //能够匹配book表中任意一行数据
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        //数据库帮助类
        databaseHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 1);
        return true;
    }

    /**
     * 从内容提供器中查询数据  查询的结果存放在Cursor对象中返回
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return 查询到的数据结果集  cursor
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = database.query("book", null, null, null, null, null, null);
                break;
            case BOOK_ITEM:
                //getPathSegments()方法，它会将内容URI权限之后的部分以“/”符号进行分割，并把分割后的结果放入到一个字符串列表中，那这个列表的第0个位置存放的就是路径，第1个位置存放的就是id了
                //取出指定的id
                String bookId = uri.getPathSegments().get(1);
                cursor = database.query("book", projection, " id = ? ", new String[]{bookId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = database.query("Category", null, null, null, null, null, null);
                break;
            case CATEGORY_ITEM:
                //取出指定的id
                String categoryId = uri.getPathSegments().get(1);
                cursor = database.query("Category", projection, "id = ? ", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
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
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.me.diankun.chapter6_data.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.me.diankun.chapter6_data.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.me.diankun.chapter6_data.Category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.me.diankun.chapter6_data.Category";
            default:
                break;
        }
        return null;
    }


    /**
     * 向内容提供器中添加一条数据。使用uri参数来确定要添加到的表，
     * 待添加的数据保存在values参数中。添加完成后，。
     *
     * @param uri
     * @param contentValues
     * @return 返回一个用于表示这条新记录的URI
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Uri returnUri = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long bookId = database.insert("book", null, contentValues);
                returnUri = Uri.parse("content://" + AUTHORITY + "/book/" + bookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long categoryId = database.insert("Category", null, contentValues);
                returnUri = Uri.parse("content://" + AUTHORITY + "/Category/" + categoryId);
                break;
            default:
                break;
        }
        return returnUri;
    }

    /**
     * 从内容提供器中删除数据。使用uri参数来确定删除哪一张表中的数据
     *
     * @param uri
     * @param s
     * @param strings
     * @return 被删除的行数将作为返回值返
     */
    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        int number = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                database.delete("book", null, null);
                break;
            case BOOK_ITEM:
                //取出指定的id
                String bookId = uri.getPathSegments().get(1);
                number = database.delete("book", " id = ? ", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                number = database.delete("Category", null, null);
                break;
            case CATEGORY_ITEM:
                //取出指定的id
                String categoryId = uri.getPathSegments().get(1);
                number = database.delete("Category", " id = ? ", new String[]{categoryId});
                break;
            default:
                break;
        }
        return number;
    }

    /**
     * 更新内容提供器中已有的数据。使用uri参数来确定更新哪一张表中的数据，
     *
     * @param uri
     * @param contentValues
     * @param s
     * @param strings
     * @return 受影响的行数将作为返回值返回
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        int number = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                database.update("book", contentValues, null, null);
                break;
            case BOOK_ITEM:
                //取出指定的id
                String bookId = uri.getPathSegments().get(1);
                number = database.update("book", contentValues, " id = ? ", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                database.update("Category", contentValues, null, null);
                break;
            case CATEGORY_ITEM:
                //取出指定的id
                String categoryId = uri.getPathSegments().get(1);
                number = database.update("Category", contentValues, " id = ? ", new String[]{categoryId});
                break;
            default:
                break;
        }
        return number;
    }
}
