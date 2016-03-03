package com.me.diankun.chapter10_net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by diankun on 2016/3/2.
 */
public class JSONActivity extends AppCompatActivity {

    private Button btn_normal_json;
    private Button btn_gson_json;

    private static final String TAG = JSONActivity.class.getSimpleName();
    private static final String URL = "http://192.168.0.108:8080/examples/get_data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        btn_normal_json = (Button) findViewById(R.id.btn_normal_json);
        btn_gson_json = (Button) findViewById(R.id.btn_gson_json);
        btn_normal_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalParseJSON();
            }
        });
        btn_gson_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsonParseJSON();
            }
        });


    }

    /**
     * 使用Gson解析JSON
     */
    private void gsonParseJSON() {
        HttpUtils.getHttpResponse(URL, new HttpUtils.onHttpListener() {
            @Override
            public void onFinish(String response) {
                Gson gson =new Gson();
                List<App> appList = gson.fromJson(response,new TypeToken<List<App>>(){}.getType());
                for (App app : appList) {
                    Log.i(TAG, "Gson 解析 app = " + app);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /**
     * 使用常见方式解析JSON
     */
    private void normalParseJSON() {
        HttpUtils.getHttpResponse(URL, new HttpUtils.onHttpListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray == null) return;
                    JSONObject jsonObject = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.optInt("id");
                        String version = jsonObject.optString("name");
                        String name = jsonObject.optString("version");
                        Log.i(TAG, " id = " + id + "\t name = " + name + "\t version = " + version);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
