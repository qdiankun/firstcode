package com.me.diankun.chapter12_sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class AcceleSensorActivity extends AppCompatActivity {

    private SensorManager mSensorManager;

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            // 加速度可能会是负值，所以要取它们的绝对值
            float xValue = Math.abs(sensorEvent.values[0]);
            float yValue = Math.abs(sensorEvent.values[1]);
            float zValue = Math.abs(sensorEvent.values[2]);
            Log.i("TAG", "xValue = " + xValue + "\t yValue = " + yValue + "\t zValue = " + zValue);
            //任务用户摇动的手机，触发摇一摇逻辑
            if (xValue > 15 || yValue > 15 || zValue > 15) {
                //Toast.makeText(AcceleSensorActivity.this, "摇一摇", Toast.LENGTH_SHORT).show();
                Log.i("TAG", "摇一摇");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private ShakeListener shakeListener = new ShakeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accele_sensor);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = mSensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
        //注册监听
        //mSensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        shakeListener.setOnShakeListener(new OnShakeListener() {
            @Override
            public void onShake() {
                Toast.makeText(AcceleSensorActivity.this, "摇一摇", Toast.LENGTH_SHORT).show();
            }
        });
        mSensorManager.registerListener(shakeListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(listener);
        }
    }


    /**
     * 自定义SensorEventListener
     */
    class ShakeListener implements SensorEventListener {

        private static final int SPEED_SHRESHOLD = 450;//这个值越大需要越大的力气来摇晃手机
        private static final int UPTATE_INTERVAL_TIME = 50;
        private long lastUpdateTime;

        private float lastX;
        private float lastY;
        private float lastZ;

        private OnShakeListener onShakeListener = null;

        public void setOnShakeListener(OnShakeListener listener) {
            this.onShakeListener = listener;
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            long currentUpdateTime = System.currentTimeMillis();
            long timeInterval = currentUpdateTime - lastUpdateTime;
            if (timeInterval < UPTATE_INTERVAL_TIME)
                return;
            lastUpdateTime = currentUpdateTime;

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;

            lastX = x;
            lastY = y;
            lastZ = z;
            double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000;
            Log.i("TAG", "speed = "+speed);
            if (speed >= SPEED_SHRESHOLD) {
                onShakeListener.onShake();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }


    public interface OnShakeListener {
        void onShake();
    }
}
