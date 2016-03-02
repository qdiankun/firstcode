package com.me.diankun.chapter6_data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	private Context mContext;

	private static final String CREATE_BOOK = "create table book(" + "id integer primary key autoincrement,"
			+ "author text," + "price real," + "pages integer," + "name text)";

	private static final String CRATE_CATEGORY = "create table Category (" + " id integer primary key autoincrement,"
			+ " category_name text," + " category_code integer)";

	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.mContext = context;
	}

	// 版本1的写法
	 @Override
	 public void onCreate(SQLiteDatabase db) {
		 db.execSQL(CREATE_BOOK);
		 ToastUtils.showShort("Create Succeeded");
	 }

	// 暴力的写法，丢失数据
	// @Override
	// public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	// {
	// db.execSQL("drop table if exists Book");
	// db.execSQL("drop table if exists Category");
	// onCreate(db);
	// }

	// 新的写法
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		// 版本2的写法;首次安装的就是版本2时，直接创建了这两张表
//		db.execSQL(CREATE_BOOK);
//		db.execSQL(CRATE_CATEGORY);
//		ToastUtils.showShort("Create Succeeded");
//	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (oldVersion) {
			// 升级数据库1的时候，Book表已经创建好了，只要再创建Category表
			// 注意，switch没有使用break跳出循环，每个case都执行一次
			case 1:
				db.execSQL(CRATE_CATEGORY);
			case 2:
				// db.execSQL(CRATE_OTHER);
			default:
				break;
		}
	}

}
