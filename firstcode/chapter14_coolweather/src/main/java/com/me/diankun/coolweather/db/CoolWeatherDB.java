package com.me.diankun.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.me.diankun.coolweather.db.CoolWeatherOpenHelper;
import com.me.diankun.coolweather.model.City;
import com.me.diankun.coolweather.model.Country;
import com.me.diankun.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;


public class CoolWeatherDB
{
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	private static CoolWeatherDB coolWeatherDB;

	private SQLiteDatabase db;

	/**
	 * 将构造方法私有化
	 */
	private CoolWeatherDB(Context context)
	{
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取CoolWeather的实例
	 *
	 * @param context
	 * @return
	 */
	public synchronized static CoolWeatherDB getInstance(Context context)
	{
		if (coolWeatherDB == null)
		{
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}

	/**
	 * 将Province 实例存储到数据库
	 *
	 * @param province
	 */
	public void saveProvince(Province province)
	{
		if (province != null)
		{
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * 从数据库中读取全国所有省份的信息
	 *
	 * @return
	 */
	public List<Province> loadProvinces()
	{
		List<Province> list = new ArrayList<Province>();
		Province province = null;
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		while (cursor.moveToNext())
		{
			province = new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvinceCode(cursor.getString(cursor
					.getColumnIndex("province_code")));
			province.setProvinceName(cursor.getString(cursor
					.getColumnIndex("province_name")));
			list.add(province);
		}
		cursor.close();
		return list;
	}

	/**
	 * 将City实例存储到数据库中
	 *
	 * @param city
	 */
	public void saveCity(City city)
	{
		ContentValues values = new ContentValues();
		values.put("city_name", city.getCityName());
		values.put("city_code", city.getCityCode());
		values.put("province_id", city.getProvinceId());
		db.insert("city", null, values);
	}

	/**
	 * 从数据库中读取某省份下所有的城市信息
	 *
	 * @param provinceId
	 * @return
	 */
	public List<City> loadCities(int provinceId)
	{
		List<City> list = new ArrayList<City>();
		City city = null;
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]
				{ String.valueOf(provinceId) }, null, null, null);
		while (cursor.moveToNext())
		{
			city = new City();
			city.setId(cursor.getInt(cursor
					.getColumnIndex("city_name")));
			city.setCityName(cursor.getString(cursor
					.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(String.valueOf(provinceId));
			list.add(city);
		}
		return list;
	}

	/**
	 * 将Country实例存储到数据库中
	 *
	 * @param country
	 */
	public void saveCountry(Country country)
	{
		ContentValues values = new ContentValues();
		values.put("country_name", country.getCountryName());
		values.put("country_code", country.getCountryCode());
		values.put("city_id", country.getCityId());
		db.insert("country", null, values);
	}

	/**
	 * 从数据库中读取某个城市所有县的信息
	 *
	 * @param cityId
	 * @return
	 */
	public List<Country> loadCountries(int cityId)
	{
		List<Country> list = new ArrayList<Country>();
		Country country = null;
		Cursor cursor = db.query("Country", null, "city_id = ?", new String[]
				{ String.valueOf(cityId) }, null, null, null);
		while (cursor.moveToNext())
		{
			country = new Country();
			country.setId(cursor.getInt(cursor.getColumnIndex("id")));
			country.setCountryCode(cursor.getString(cursor
					.getColumnIndex("country_code")));
			country.setCountryName(cursor.getString(cursor
					.getColumnIndex("country_name")));
			country.setCityId(String.valueOf(cityId) );
			list.add(country);
		}
		return list;
	}
}
