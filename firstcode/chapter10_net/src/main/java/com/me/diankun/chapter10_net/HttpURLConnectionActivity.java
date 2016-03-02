package com.me.diankun.chapter10_net;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by diankun on 2016/3/2.
 */
public class HttpURLConnectionActivity extends AppCompatActivity {

    private Button btn_get_baidu;
    private TextView tv_response;

    private static final String URL = "http://wthrcdn.etouch.cn/WeatherApi?citykey=101010100";
    private static final String BAIDU_URL = "https://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpurlconnection);

        btn_get_baidu = (Button) findViewById(R.id.btn_get_baidu);
        tv_response = (TextView) findViewById(R.id.tv_response);
        btn_get_baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadBaiduAsyncTask().execute();
            }
        });
    }

    private class LoadBaiduAsyncTask extends AsyncTask<Void, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(HttpURLConnectionActivity.this, "download ... ", "正在查询百度数据...", true, false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = getBaiduResponse();
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            tv_response.setText(s);
        }
    }

    private String getBaiduResponse() {

        HttpURLConnection connection = null;
        BufferedReader br = null;
        InputStream inputStream = null;
        //拼接字符串
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();

            //设置连接超时，请求超时
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            //得到请求码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                String str = "";
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (br != null) {
                    br.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return sb.toString();
    }
}
