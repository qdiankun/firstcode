package com.me.diankun.chapter6_data;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by diankun on 2016/3/2.
 */
public class SaveToFileActvity extends AppCompatActivity {

    private EditText mFileDataEdit;
    private static final String FILE_DATA = "filedata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveto_file);

        mFileDataEdit = (EditText) findViewById(R.id.et_file_data);
        //读取文件中的数据,写入到EditText中去
        String inputText = loadFileData();
        if (!TextUtils.isEmpty(inputText)) {
            mFileDataEdit.setText(inputText);
            //设置光标到末尾
            mFileDataEdit.setSelection(inputText.length());
        }
    }

    private String loadFileData() {

        FileInputStream fis = null;
        BufferedReader br = null;
        //保存读取的数据
        StringBuffer sb = new StringBuffer();
        try {
            fis = openFileInput(FILE_DATA);
            br = new BufferedReader(new InputStreamReader(fis));
            String str = "";
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        String fileDataStr = mFileDataEdit.getText().toString();
        if (!TextUtils.isEmpty(fileDataStr)) {
            saveFileData(fileDataStr);
        }
    }

    /**
     * 保存数据到文件中
     *
     * @param fileDataStr
     */
    private void saveFileData(String fileDataStr) {

        BufferedWriter bw = null;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_DATA, Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(fileDataStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
