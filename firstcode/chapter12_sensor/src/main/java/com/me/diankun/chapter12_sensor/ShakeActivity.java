package com.me.diankun.chapter12_sensor;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

/**
 * 仿照git-osc-android 来实现 摇一摇功能
 * <p/>
 * Created by diankun on 2016/3/10.
 */
public class ShakeActivity extends AppCompatActivity {


    private final int DURATION_TIME = 600;

    //private AppContext mAppContext;

    private ShakeListener mShakeListener = null;

    private Vibrator mVibrator;

    private TextView mLuckMsg;

    private RelativeLayout mImgUp;

    private RelativeLayout mImgDn;

    private LinearLayout mLoaging;

    //private RelativeLayout mShakeResProject;// 摇到项目

    private ImageView mProjectFace;

    private TextView mProjectTitle;

    private TextView mProjectDescription;

    private TextView mProjectLanguage;

    private TextView mProjectWatchNums;

    private TextView mProjectStarNums;

    private TextView mProjectForkNums;

    private RelativeLayout mShakeResAward;// 摇到奖品

    private ImageView mShakeResAwardImg;

    private TextView mShakeResAwardMsg;

    //private RandomProject mProject;

    private SoundPool sndPool;
    private HashMap<Integer, Integer> soundPoolMap = new HashMap<>();

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        initView();

        loadSound();
        mShakeListener = new ShakeListener(this);
        // 监听到手机摇动
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                startAnim();
            }
        });
    }

    private void startAnim() {

        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -0.5f);
        mytranslateanimup0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.5f);
        mytranslateanimup1.setDuration(DURATION_TIME);
        mytranslateanimup1.setStartOffset(DURATION_TIME);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        mImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.5f);
        mytranslateanimdn0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -0.5f);
        mytranslateanimdn1.setDuration(DURATION_TIME);
        mytranslateanimdn1.setStartOffset(DURATION_TIME);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        mImgDn.startAnimation(animdn);

        // 动画监听，开始时显示加载状态，
        mytranslateanimdn0.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                //mShakeResProject.setVisibility(View.GONE);
                mShakeListener.stop();
                sndPool.play(soundPoolMap.get(0), (float) 0.2, (float) 0.2, 0, 0,
                        (float) 0.6);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadProject();
            }
        });
    }

    private void loadProject() {
        Toast.makeText(this, "加载到一个工程", Toast.LENGTH_SHORT).show();
        afterLoading();
    }


    private void afterLoading() {
        mLoaging.setVisibility(View.GONE);
        sndPool.play(soundPoolMap.get(1), (float) 0.2, (float) 0.2,
                0, 0, (float) 0.6);
        mShakeListener.start();
    }

    private void loadSound() {
        sndPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        new Thread() {
            public void run() {
                try {
                    soundPoolMap.put(0, sndPool.load(getAssets().openFd("sound/shake_sound_male.mp3"), 1));
                    soundPoolMap.put(1, sndPool.load(getAssets().openFd("sound/shake_match.mp3"), 1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        mLuckMsg = (TextView) findViewById(R.id.shake_luck_msg);

        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);

        mLoaging = (LinearLayout) findViewById(R.id.shake_loading);

        //mShakeResProject = (RelativeLayout) findViewById(R.id.shakeres_paroject);

        mProjectFace = (ImageView) findViewById(R.id.iv_portrait);

        mProjectTitle = (TextView) findViewById(R.id.tv_title);

        mProjectDescription = (TextView) findViewById(R.id.tv_description);

        mProjectLanguage = (TextView) findViewById(R.id.tv_lanuage);

        mProjectWatchNums = (TextView) findViewById(R.id.tv_watch);

        mProjectStarNums = (TextView) findViewById(R.id.tv_star);

        mProjectForkNums = (TextView) findViewById(R.id.tv_fork);

        mShakeResAward = (RelativeLayout) findViewById(R.id.shakeres_award);

        mShakeResAwardImg = (ImageView) findViewById(R.id.shake_award_img);

        mShakeResAwardMsg = (TextView) findViewById(R.id.shake_award_msg);

        //mShakeResProject.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }
}
