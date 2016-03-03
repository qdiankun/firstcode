package com.me.diankun.chapter5_broadcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by diankun on 2016/3/3.
 */
public class ForceOfflineActivity extends Activity {

    private EditText et_username;
    private EditText et_password;
    private Button   btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forceline);


        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if ("admin".equals(username) && "paswd".equals(password)) {
                    Intent i = new Intent(ForceOfflineActivity.this, ContentActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}
