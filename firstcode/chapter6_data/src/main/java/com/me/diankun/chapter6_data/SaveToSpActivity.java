package com.me.diankun.chapter6_data;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/2.
 */
public class SaveToSpActivity extends AppCompatActivity {

    @Bind(R.id.et_sp_data)
    EditText mSpEdit;

    @Bind(R.id.tv_sp_data)
    TextView mSpText;

    @Bind(R.id.et_username)
    EditText mUsernameEdit;

    @Bind(R.id.et_password)
    EditText mPaswdEdit;

    @Bind(R.id.cb_is_remember)
    CheckBox mRememberBox;

    @Bind(R.id.btn_login)
    Button mLoginBtn;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "paswd";
    private static final String ISREMEMBER = "isremember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveto_sp);
        ButterKnife.bind(this);

        mPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mEditor = mPreferences.edit();

        //判断是否记住密码
        boolean isRemember = mPreferences.getBoolean(ISREMEMBER, false);
        if (isRemember) {
            String username = mPreferences.getString(USERNAME, "");
            String paswd = mPreferences.getString(PASSWORD, "");
            mUsernameEdit.setText(username);
            mPaswdEdit.setText(paswd);
            mRememberBox.setChecked(true);
        }
    }


    @OnClick(R.id.btn_login)
    void login() {
        String username = mUsernameEdit.getText().toString();
        String paswd = mPaswdEdit.getText().toString();
        //登录成功的情况下判断是否保存密码
        if ("admin".equals(username) && "123".equals(paswd)) {
            //是否选中保存密码
            if (mRememberBox.isChecked()) {
                mEditor.putBoolean(ISREMEMBER, true);
                mEditor.putString(USERNAME, username);
                mEditor.putString(PASSWORD, paswd);
                Toast.makeText(this, "记住密码到SharedPreferences",
                        Toast.LENGTH_SHORT).show();
            } else {
                //清除所有记录
                mEditor.clear();
            }
            mEditor.commit();
        }
    }


    @OnClick(R.id.btn_savedata_sp)
    void SaveDataToSp(View view) {
        String spDataStr = mSpEdit.getText().toString();
        if (TextUtils.isEmpty(spDataStr)) {
            Toast.makeText(this, "请输入SP内容", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences preferences = getSharedPreferences("person", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", spDataStr);
        editor.commit();
        Toast.makeText(this, "保存   " + spDataStr + "  到SharedPreferences",
                Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.btn_getdata_sp)
    void GetFromSp(View view) {
        SharedPreferences preferences = getSharedPreferences("person",
                MODE_PRIVATE);
        String name = preferences.getString("name", "Default Name");
        mSpText.setText(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
