package com.me.diankun.chapter8_media;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

/**
 * Created by diankun on 2016/3/8.
 */
public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_play;
    private Button btn_pause;
    private Button btn_stop;

    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        //初始化MediaPlayer
        initMediaPlayer();

    }

    private void initMediaPlayer() {

//        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "documents" + File.separator + "Bad.mp3");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Bad.mp3");
        Log.i("TAG", "path = " + file.exists());
        try {
            //指定音频文件的路径
            mMediaPlayer.setDataSource(file.getPath());
            Log.i("TAG", "path = " + file.getPath());
            //让MediaPlayer进入到准备状态
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_play:
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();
                }
                break;
            case R.id.btn_pause:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
            case R.id.btn_stop:
                if (mMediaPlayer.isPlaying()) {
                    //停止播放，重新调用initMediaPlayer()
                    mMediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}
