package com.example.andy.connectutil.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andy.connectutil.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 95815 .
 * Date:2017/3/31.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class CountDownFragment extends BaseFragment {

    public static final String fragment_tag = "CountDownFragment";


    private int count=0;
    private Handler mHandler;
    private Handler mHandler1;
    private Timer timer;
    private ImageView img_gif_wifi;
    private TextView tv_countdown;

    public static CountDownFragment newInstance() {

        Bundle args = new Bundle();
        CountDownFragment fragment = new CountDownFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_view_countdown;
    }

    @Override
    public void initView(View view) {
        img_gif_wifi = obtainView(view, R.id.img_countdown);
        tv_countdown = obtainView(view, R.id.tv_countdown);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        mHandler1 = new Handler();
        mHandler1.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(getActivity()).load(R.drawable.wifi).asGif().into(img_gif_wifi);
            }
        });
        startCountDown();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 倒计时
     */
    public void startCountDown() {

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what > 0) {
                    tv_countdown.setText(msg.what + "s");
                } else {
                    if (msg.what == 0) {
                        tv_countdown.setText("0s");
                        timer.cancel();
                        holderListener.setMainPage("设备连接失败",View.VISIBLE);
                        mHandler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holderListener.startWifiConnection("返回");
                            }
                        },3000);
//                        timer.schedule(new TimerTask() {
//
//                            @Override
//                            public void run() {
//                                if(count==0){
//                                    count++;
//                                }
//                            }
//                        },1000,1000);


                    }
                }
            }

        };


        timer = new Timer(true);
        TimerTask tt = new TimerTask() {
            int countTime = 60;
            @Override
            public void run() {
                if (countTime > 0) {
                    countTime--;
                }
                Message msg = new Message();
                msg.what = countTime;
                mHandler.sendMessage(msg);

            }
        };
        timer.schedule(tt, 1000, 1000);

    }
}
