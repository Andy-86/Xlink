package com.example.andy.connectutil.Activity;


import com.example.andy.connectutil.R;

/**
 * Created by 95815 .
 * Date:2017/4/4.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class LanguageActivity extends BarActivity {


    @Override
    protected void initData() {
        super.initData();
        getTvTitle().setText("LED");
    }

    @Override
    protected int getLayoutId() {
       // return super.getLayoutId();
        return R.layout.fragment_view_led;
    }
}
