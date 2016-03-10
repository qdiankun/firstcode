package com.me.diankun.coolweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.diankun.coolweather.R;
import com.me.diankun.coolweather.service.AutoUpdateService;
import com.me.diankun.coolweather.utils.HttpCallbackListener;
import com.me.diankun.coolweather.utils.HttpUtil;
import com.me.diankun.coolweather.utils.Utility;

/**
 * Created by diankun on 2016/3/10.
 */
public class WeatherActivity  extends Activity implements View.OnClickListener
{

    /**
     * 显示城市名
     */
    private TextView cityNameText;
    /**
     * 显示发布时间
     */
    private TextView publishTimeText;
    /**
     * 显示天气描述信息
     */
    private TextView weatherDespText;
    /**
     * 气温1
     */
    private TextView temp1Text;
    /**
     * 气温2
     */
    private TextView temp2Text;
    /**
     * 显示当前时间
     */
    private TextView currentTimeText;

    /**
     * 切换城市
     */
    private Button mHomeBtn;
    /**
     * 更新天气
     */
    private Button mRefreshBtn;

    private LinearLayout weatherInfoLayout;
    private String countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        initView();
    }

    private void initView()
    {
        cityNameText = (TextView) findViewById(R.id.tv_cityname);
        publishTimeText = (TextView) findViewById(R.id.tv_publish_time);
        weatherDespText = (TextView) findViewById(R.id.tv_weather_descp);
        temp1Text = (TextView) findViewById(R.id.tv_temp1);
        temp2Text = (TextView) findViewById(R.id.tv_temp2);
        currentTimeText = (TextView) findViewById(R.id.tv_current_date);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.ll_weather_info);
        mHomeBtn = (Button) findViewById(R.id.btn_home);
        mRefreshBtn = (Button) findViewById(R.id.btn_refresh);
        mHomeBtn.setOnClickListener(this);
        mRefreshBtn.setOnClickListener(this);

        countryCode = getIntent().getStringExtra("country_code");
        Log.i("dklog", "countryCode = " + countryCode);
        if (!TextUtils.isEmpty(countryCode))
        {
            //保存countryCode到sp中
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            sp.edit().putString("country_code", countryCode).commit();
            // 有县级代号时，查询县级天气
            publishTimeText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countryCode);
        } else
        {
            // 没有县级代号，查询本地天气
            showWeather();
        }
    }

    /**
     * 从sharedpreference中读取存储的天气信息，显示在界面
     */
    private void showWeather()
    {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        countryCode = sp.getString("country_code", "");
        cityNameText.setText(sp.getString("city_name", ""));
        currentTimeText.setText(sp.getString("current_time", ""));
        weatherDespText.setText(sp.getString("weather_desp", ""));
        publishTimeText.setText(sp.getString("publish_time", "") + " 发布");
        temp1Text.setText(sp.getString("temp1", ""));
        temp2Text.setText(sp.getString("temp2", ""));
        // 显示数据
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);

        //定时更新数据
        Intent intent = new Intent(this,AutoUpdateService.class);
        startService(intent);
    }

    private void queryWeatherCode(String countryCode)
    {
        String address = "http://www.weather.com.cn/data/cityinfo/"
                + countryCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    private void queryFromServer(String address, final String type)
    {
        Log.i("dklog", "address = " + address);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

            @Override
            public void onFinish(String response) {
                Log.i("dklog", "response = " + response);
                if ("weatherCode".equals(type)) {
                    // 处理从服务器返回的数据
                    Utility.handleWeatherResponse(WeatherActivity.this,
                            response);
                    // 显示数据
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        publishTimeText.setText("同步失败");
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_home:
                // 跳转到主界面
                Intent intent = new Intent(WeatherActivity.this,
                        ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_refresh:
                // 有县级代号时，查询县级天气
                publishTimeText.setText("同步中...");
                queryWeatherCode(countryCode);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

}