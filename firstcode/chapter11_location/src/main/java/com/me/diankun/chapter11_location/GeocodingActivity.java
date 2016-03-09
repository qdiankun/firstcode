package com.me.diankun.chapter11_location;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.me.diankun.chapter11_location.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by diankun on 2016/3/9.
 */
public class GeocodingActivity extends AppCompatActivity {

    private double latitude = 31.985237;
    private double longitude = 120.900301;
    private Button btn_geocoding;
    private TextView tv_address;

    private static final int SHOW_LOCATION = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_LOCATION:
                    tv_address.setText((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_latlon);

        btn_geocoding = (Button) findViewById(R.id.btn_geocoding);
        tv_address = (TextView) findViewById(R.id.tv_address);
        btn_geocoding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoLatLong();
            }
        });

    }

    private void geoLatLong() {
        String url = "http://maps.googleapis.com/maps/api/geocode/json?" + "latlng=" + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&sensor=false";
        Log.d("TAG", "url = " + url);
        HttpUtils.getHttpResponse(url, new HttpUtils.onHttpListener() {
            @Override
            public void onFinish(String response) {
                Log.i("TAG", "response = " + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("results");
                    if (jsonArray.length() > 0) {
                        JSONObject object = jsonArray.getJSONObject(0);
                        String address = object.getString("formatted_address");
                        Message message = mHandler.obtainMessage();
                        message.obj = address;
                        message.what = SHOW_LOCATION;
                        mHandler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
