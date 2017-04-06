package com.example.andy.connectutil.Activity;


import com.example.andy.connectutil.R;

/**
 * Created by 95815 .
 * Date:2017/4/4.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class AboutActivity extends BarActivity {

    @Override
    protected void initData() {

        super.initData();
        getTvTitle().setText("关于本机");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

}
