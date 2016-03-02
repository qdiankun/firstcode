package com.me.diankun.chapter10_net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by diankun on 2016/3/2.
 */
public class XMLActivity extends AppCompatActivity {


    private Button btn_use_pull;

    private static final String TAG = XMLActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        Log.i(TAG, "Thread Id = " + Thread.currentThread().getId());

        btn_use_pull = (Button) findViewById(R.id.btn_use_pull);
        btn_use_pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtils.getHttpResponse("http://192.168.1.115:8080/examples/get_data.xml", new HttpUtils.onHttpListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.i(TAG, "onFinish Thread Id = " + Thread.currentThread().getId());
                        Log.i("TAG", "response = " + response);

                        parseXMLWithPull(response);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i("TAG", "exception = " + e.getMessage());
                    }
                });
            }
        });
    }

    private void parseXMLWithPull(String response) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(response));


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
