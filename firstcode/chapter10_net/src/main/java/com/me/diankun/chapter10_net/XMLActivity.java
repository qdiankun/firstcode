package com.me.diankun.chapter10_net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by diankun on 2016/3/2.
 */
public class XMLActivity extends AppCompatActivity {


    private Button btn_use_pull;
    private Button btn_use_sax;

    private static final String TAG = XMLActivity.class.getSimpleName();
    private static final String URL = "http://192.168.0.108:8080/examples/get_data.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        Log.i(TAG, "Thread Id = " + Thread.currentThread().getId());

        btn_use_pull = (Button) findViewById(R.id.btn_use_pull);
        btn_use_pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtils.getHttpResponse(URL, new HttpUtils.onHttpListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.i(TAG, "onFinish Thread Id = " + Thread.currentThread().getId());
                        Log.i("TAG", "response = " + response);
                        List<App> appList = parseXMLWithPull(response);
                        if (appList == null) return;
                        for (App app : appList) {
                            Log.i("TAG", "PULL 解析结果 app = " + app);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i("TAG", "exception = " + e.getMessage());
                    }
                });
            }
        });


        ///////是SAX解析XML
        btn_use_sax = (Button) findViewById(R.id.btn_use_sax);
        btn_use_sax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtils.getHttpResponse(URL, new HttpUtils.onHttpListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.i(TAG, "onFinish Thread Id = " + Thread.currentThread().getId());
                        Log.i("TAG", "response = " + response);
                        parseXMLWithSAX(response);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

    }

    /**
     * 使用SAXL解析XML
     *
     * @param response
     */
    private void parseXMLWithSAX(String response) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            MyHandler handler = new MyHandler();
            //将MyHandler的实例加入到XMLReader中
            xmlReader.setContentHandler(handler);
            //开始解析
            xmlReader.parse(new InputSource(new StringReader(response)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用PULL解析XML，返回解析结果
     *
     * @param response
     * @return
     */
    private List<App> parseXMLWithPull(String response) {

        //初始化数据
        List<App> appList = null;
        App app = null;

        try {
            // 创建XmlPullParserFactory解析工厂
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            // 通过一个factory实例化一个parser
            XmlPullParser xmlPullParser = factory.newPullParser();
            // 指定编码解析xml
            xmlPullParser.setInput(new StringReader(response));

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    // 解析文档开始的时候
                    case XmlPullParser.START_DOCUMENT:
                        appList = new ArrayList<App>();
                        break;
                    // 解析文档起始标签
                    case XmlPullParser.START_TAG:
                        if ("app".equals(xmlPullParser.getName())) {
                            app = new App();
                        } else if ("id".equals(xmlPullParser.getName())) {
                            app.setId(Integer.valueOf(xmlPullParser.nextText()));
                        } else if ("name".equals(xmlPullParser.getName())) {
                            app.setName(xmlPullParser.nextText());
                        } else if ("version".equals(xmlPullParser.getName())) {
                            app.setVersion(xmlPullParser.nextText());
                        }
                        break;
                    // 解析文档结束标签
                    case XmlPullParser.END_TAG:
                        if ("app".equals(xmlPullParser.getName())) {
                            appList.add(app);
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appList;
    }
}
