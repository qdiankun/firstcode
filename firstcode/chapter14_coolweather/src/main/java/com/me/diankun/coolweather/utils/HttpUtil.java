package com.me.diankun.coolweather.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by diankun on 2016/3/10.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address,
                                       final HttpCallbackListener listener)
    {
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                HttpURLConnection connection = null;
                try
                {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    // 设置请求参数
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }

                    if(listener!=null)
                    {
                        // 回调onFinish()函数
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e)
                {
                    if(listener!=null)
                    {
                        // 回调onError()函数
                        listener.onError(e);
                    }
                } finally
                {

                }
            }
        }).start();
    }
}
