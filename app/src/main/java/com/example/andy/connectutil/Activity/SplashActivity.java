package com.example.andy.connectutil.Activity;

import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.andy.connectutil.R;


/**
 * Created by 95815 .
 * Date:2017/3/29.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 * 描述：引导页
 */

public class SplashActivity extends BasicActivity {
    private ImageView mRlSplash;


    @Override
    protected void setBefortLayout() {

    }

    @Override
    protected void setActionbar() {

    }

    @Override
    protected void initView() {
        mRlSplash = obtainView(R.id.image_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setListener() {

    }

    private void start() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                involeActivity(SplashActivity.this, MainActivity.class);
                finish();
            }
        }, 3000);


    }

    /**
     * 引导页动画
     */
    private void startAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                involeActivity(SplashActivity.this, MainActivity.class);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRlSplash.startAnimation(alphaAnimation);
    }


}
