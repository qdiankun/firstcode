package com.me.diankun.coolweather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diankun on 2016/3/10.
 */
public class Utility {

    /**
     * 解析从服务器返回的json数据，并将解析的数据存储到本地
     *
     * @param context
     * @param response
     */
    public static void handleWeatherResponse(Context context, String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("city");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
                    weatherDesp, publishTime);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的天气信息保存到本地的SharedPreference中
     * @param context
     * @param cityName
     * @param weatherCode
     * @param temp1
     * @param temp2
     * @param weatherDesp
     * @param publishTime
     */
    private static void saveWeatherInfo(Context context, String cityName,
                                        String weatherCode, String temp1, String temp2, String weatherDesp,
                                        String publishTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("city_selected", true);
        edit.putString("city_name", cityName);
        edit.putString("weather_code", weatherCode);
        edit.putString("temp1", temp1);
        edit.putString("temp2", temp2);
        edit.putString("weather_desp", weatherDesp);
        edit.putString("publish_time", publishTime);
        edit.putString("current_time", sdf.format(new Date()));
        edit.commit();
    }


}
