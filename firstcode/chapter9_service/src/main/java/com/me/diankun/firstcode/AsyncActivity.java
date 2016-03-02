package com.me.diankun.firstcode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
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
            int version = 0;
            //请求服务器数据
            String str = getServerVesion();
            try {
                JSONObject jsonObject = new JSONObject(str);
                version = jsonObject.optInt("version");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return version;
        }


        @Override
        protected void onPostExecute(Integer version) {


            if (version == 0) {
                Toast.makeText(AsyncActivity.this, "无需更新版本", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AsyncActivity.this, "更新版本", Toast.LENGTH_SHORT).show();
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
        return "{\"version\":0}";
    }
}
