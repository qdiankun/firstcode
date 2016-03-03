package com.me.diankun.chapter10_net;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 使用SAX解析
 * Created by diankun on 2016/3/2.
 */
public class MyHandler extends DefaultHandler {

    private static final String TAG = MyHandler.class.getSimpleName();

    //记录当前节点
    private String nodeName;

    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;

    /**
     * 开始XMl解析时调用
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    /**
     * 开始解析某个节点时调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //记录当前节点
        nodeName = localName;
    }


    /**
     * 获取节点中内容时调用
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("id".equals(nodeName)) {
            id.append(ch, start, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, start, length);
        }
    }


    /**
     * 完成解析某个节点时调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)) {
            Log.i(TAG, "SAX 解析 app : id=" + id.toString().trim() + "\t name=" + name.toString().trim() + "\t version=" + version.toString().trim());

            //清空,为解析下一个节点使用
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }

    }


    @Override
    public void endDocument() throws SAXException {

    }

}
