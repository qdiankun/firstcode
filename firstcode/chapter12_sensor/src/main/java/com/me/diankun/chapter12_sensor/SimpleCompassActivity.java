package com.me.diankun.chapter12_sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SimpleCompassActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private ImageView compassImg;

    private static final int UPTATE_INTERVAL_TIME = 50;

    //用于修改图片的透明度
    private ImageView imageview;

    private SensorEventListener listener = new SensorEventListener() {

        private long lastUpdateTime = 0;

        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];

        private float lastRotateDegree;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            //限定更新时间
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdateTime > UPTATE_INTERVAL_TIME) {


                // 判断当前是加速度传感器还是地磁传感器
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    // 注意赋值时要调用clone()方法
                    accelerometerValues = sensorEvent.values.clone();
                } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    // 注意赋值时要调用clone()方法
                    magneticValues = sensorEvent.values.clone();
                }

                float[] R = new float[9];
                float[] values = new float[3];
                SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
                SensorManager.getOrientation(R, values);
                //Log.d("MainActivity", "value[0] is " + Math.toDegrees(values[0]));
                // 将计算出的旋转角度取反，用于旋转指南针背景图
                float rotateDegree = -(float) Math.toDegrees(values[0]);
                if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
                    RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setFillAfter(true);
                    compassImg.startAnimation(animation);
                    lastRotateDegree = rotateDegree;
                }

                lastUpdateTime = currentTime;

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_compass);

        compassImg = (ImageView) findViewById(R.id.compass_img);
        imageview = (ImageView) findViewById(R.id.imageview);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }


    /**
     * 修改图的透明程度
     *
     * @param view
     */
    public void changeAlpha(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        imageview.startAnimation(alphaAnimation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
}
