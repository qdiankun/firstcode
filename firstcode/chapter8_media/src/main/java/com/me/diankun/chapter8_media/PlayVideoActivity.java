package com.me.diankun.chapter8_media;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by diankun on 2016/3/8.
 */
public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_play;
    private Button btn_pause;
    private Button btn_replay;

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        videoView = (VideoView) findViewById(R.id.video_view);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_replay = (Button) findViewById(R.id.btn_replay);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_replay.setOnClickListener(this);

        initVideoPath();
    }

    private void initVideoPath() {

        File videoFile = new File(Environment.getExternalStorageDirectory() + File.separator + "videos" + File.separator + "Unbroken - Motivational Video.mp4");
        //指定视频文件的路径
        videoView.setVideoPath(videoFile.getPath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
            case R.id.btn_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.btn_replay:
                if (videoView.isPlaying()) {
                    videoView.resume();//重新播放
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }
}
