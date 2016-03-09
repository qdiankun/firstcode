package com.me.diankun.chapter11_location.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by diankun on 2016/3/2.
 */
public class HttpUtils {

    public static void getHttpResponse(final String urlStr, final onHttpListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader br = null;
                InputStream inputStream = null;
                //拼接字符串
                StringBuffer sb = new StringBuffer();
                try {
                    URL url = new URL(urlStr);
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
                        listener.onFinish(sb.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onError(e);
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
            }
        }).start();
    }


    public interface onHttpListener {

        public void onFinish(String response);

        public void onError(Exception e);
    }

}
