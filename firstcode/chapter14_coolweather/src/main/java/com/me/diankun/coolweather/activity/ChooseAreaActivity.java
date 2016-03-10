package com.me.diankun.coolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.me.diankun.coolweather.R;
import com.me.diankun.coolweather.db.CoolWeatherDB;
import com.me.diankun.coolweather.model.City;
import com.me.diankun.coolweather.model.Country;
import com.me.diankun.coolweather.model.Province;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChooseAreaActivity extends Activity
{

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    private ProgressDialog mProgressDialog;
    private TextView mTitle;
    private ListView mArea;
    private ArrayAdapter<String> mAdapter;
    private CoolWeatherDB mCoolWeatherDB;
    private List<String> mDataList = new ArrayList<String>();
    /**
     * 省列表
     */
    private List<Province> provincesList;
    /**
     * 城市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<Country> countryList;
    /**
     * 选中的省
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 当前的选中级别
     */
    private int currentLevel;
    /**
     * 判断是否从WeatherActivity传递过来的
     */
    private boolean isFromWeatherActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);

        isFromWeatherActivity = getIntent().getBooleanExtra(
                "from_weather_activity", false);
        Log.i("dklog", "isFromWeatherActivity = "+isFromWeatherActivity);
        // 判断是否选中，直接进入天气界面
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sp.getBoolean("city_selected", false) && !isFromWeatherActivity)
        {
            Intent intent = new Intent(ChooseAreaActivity.this,
                    WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        mTitle = (TextView) findViewById(R.id.tv_title);
        mArea = (ListView) findViewById(R.id.lv_area);

        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDataList);
        mArea.setAdapter(mAdapter);
        mCoolWeatherDB = CoolWeatherDB.getInstance(ChooseAreaActivity.this);

        queryPrivnces();

        mArea.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                if (currentLevel == LEVEL_PROVINCE)
                {
                    selectedProvince = provincesList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY)
                {
                    selectedCity = cityList.get(position);
                    queryCountries();
                } else if (currentLevel == LEVEL_COUNTY)
                {
                    String weathercode = countryList.get(position)
                            .getCountryCode();
                    Intent intent = new Intent(ChooseAreaActivity.this,
                            WeatherActivity.class);
                    intent.putExtra("country_code", weathercode);
                    startActivity(intent);
                    finish();
                }
            }

        });
    }

    private void parserCityXml()
    {
        try
        {
            String provinceId = "";
            String cityId = "";
            Province province = null;
            City city = null;
            Country country = null;

            List<Province> provincesList = new ArrayList<Province>();
            List<City> cityList = new ArrayList<City>();
            List<Country> countryList = new ArrayList<Country>();

            InputStream is = getResources().openRawResource(R.raw.cities);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "UTF-8");
            int eventCode = parser.getEventType();
            while (eventCode != XmlPullParser.END_DOCUMENT)
            {
                switch (eventCode)
                {
                    case XmlPullParser.START_TAG:
                        if ("province".equals(parser.getName()))
                        {
                            provinceId = parser.getAttributeValue(null, "id");
                            province = new Province();
                            province.setProvinceCode(provinceId);
                            province.setProvinceName(parser.getAttributeValue(null,
                                    "name"));
                            provincesList.add(province);
                        } else if ("city".equals(parser.getName()))
                        {
                            cityId = parser.getAttributeValue(null, "id");
                            city = new City();
                            city.setProvinceId(provinceId);
                            city.setCityCode(cityId);
                            city.setCityName(parser.getAttributeValue(null, "name"));
                            cityList.add(city);
                        } else if ("county".equals(parser.getName()))
                        {
                            country = new Country();
                            country.setCityId(cityId);
                            country.setCountryCode(parser.getAttributeValue(null,
                                    "weatherCode"));
                            country.setCountryName(parser.getAttributeValue(null,
                                    "name"));
                            countryList.add(country);
                        }
                        break;
                }
                eventCode = parser.next();
            }

            for (Province p : provincesList)
            {
                // Log.i("dklog", "p = " +p);
                mCoolWeatherDB.saveProvince(p);
            }
            for (City c : cityList)
            {
                // Log.i("dklog", "c = " + c);
                mCoolWeatherDB.saveCity(c);
            }
            // Log.i("dklog", "countryList = " + countryList);
            for (Country d : countryList)
            {
                // Log.i("dklog", "d = " + d);
                mCoolWeatherDB.saveCountry(d);
            }
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void queryPrivnces()
    {
        provincesList = mCoolWeatherDB.loadProvinces();
        if (provincesList.size() > 0)
        {
            mDataList.clear();
            for (Province p : provincesList)
            {
                mDataList.add(p.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mTitle.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else
        {
            showProgressDialog();
            parserCityXml();
            closeProgressDialog();
            queryPrivnces();
        }
    }

    private void queryCities()
    {
        cityList = mCoolWeatherDB.loadCities(Integer.valueOf(selectedProvince
                .getProvinceCode()));
        if (cityList.size() > 0)
        {
            mDataList.clear();
            for (City c : cityList)
            {
                mDataList.add(c.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mTitle.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        }
    }

    private void queryCountries()
    {
        try
        {
            Log.i("dklog",
                    "cityid = " + Integer.valueOf(selectedCity.getCityCode()));
            countryList = mCoolWeatherDB.loadCountries(Integer
                    .valueOf(selectedCity.getCityCode()));
            if (countryList.size() > 0)
            {
                // 重新设置数据
                mDataList.clear();
                for (Country c : countryList)
                {
                    mDataList.add(c.getCountryName());
                }
                mAdapter.notifyDataSetChanged();
                // 设置标题
                mTitle.setText(selectedCity.getCityName());
                // 设置当前层级
                currentLevel = LEVEL_COUNTY;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog()
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(ChooseAreaActivity.this);
            mProgressDialog.setTitle("解析数据");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("正在加载....");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    /**
     * 关闭进度条对话框
     */
    private void closeProgressDialog()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 捕获Back按键，根据当前的级别判断，此时应该返回市列表，省列表，还是直接退出
     */
    @Override
    public void onBackPressed()
    {
        Log.i("dklog", "isFromWeatherActivity = " + isFromWeatherActivity);
        if (currentLevel == LEVEL_COUNTY)
        {
            queryCities();
        } else if (currentLevel == LEVEL_CITY)
        {
            queryPrivnces();
        } else
        {
            // 如果时从WeatherActivity进入的
            if (isFromWeatherActivity)
            {
                Intent intent = new Intent(ChooseAreaActivity.this,
                        WeatherActivity.class);
                startActivity(intent);
            }
        }
    }

}
