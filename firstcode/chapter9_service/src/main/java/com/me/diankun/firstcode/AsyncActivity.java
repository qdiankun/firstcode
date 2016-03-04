package com.me.diankun.firstcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 此为一道面试题
 *
 * Created by diankun on 2016/3/1.
 */
public class AsyncActivity extends AppCompatActivity {

    private static final String TAG = AsyncActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_async_activity);

        Log.i(TAG, "onCreate ThreadId = " + Thread.currentThread().getId());

        //查看服务器是否需要更新
        new UpdateAsyncTask().execute();
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {

            Log.i(TAG, "UpdateAsyncTask ThreadId = " + Thread.currentThread().getId());
            int ispudate = 0;
            //请求服务器数据
            String str = getServerVesion();
            try {
                JSONObject jsonObject = new JSONObject(str);
                ispudate = jsonObject.optInt("ispudate");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ispudate;
        }

        @Override
        protected void onPostExecute(Integer ispudate) {
            if (ispudate == 0) {
                Toast.makeText(AsyncActivity.this, "无需更新版本", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(AsyncActivity.this, "更新版本", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(AsyncActivity.this);
                builder.setTitle("版本更新")
                        .setMessage("有新版本是否更新")
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        }
    }

    /**
     * 请求服务器版本号
     *
     * @return
     */
    public String getServerVesion() {

        //模拟耗时
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //返回结果
        return "{\"ispudate\":1}";
    }
}
