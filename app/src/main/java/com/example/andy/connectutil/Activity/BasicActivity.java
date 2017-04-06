package com.example.andy.connectutil.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public abstract class BasicActivity extends AppCompatActivity
         {
    protected Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBefortLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        setActionbar();
        initView();
        initData();
        setListener();

    }

    //布局加载前的操作
    protected abstract void setBefortLayout();

    //设置toolbar
    protected abstract void setActionbar();

    //视图初始化
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    /**
     * @return LayoutId
     */
    protected abstract int getLayoutId();

    //设置监听
    protected abstract void setListener();


    /**
     * 作用：获取控件 Id (避免类型转换的繁琐，抽取 findViewById() )
     *
     * @param ReId 控件的ID
     * @param <T>  具体控件View
     * @return
     */
    public <T extends View> T obtainView(int ReId) {
        return (T) findViewById(ReId);
    }

    /**
     * @param context
     * @param clz
     */
    public void involeActivity(Context context, Class clz) {
        startActivity(new Intent(context, clz));
    }

    /**
     * @param str 显示的内容
     */
    public void showToast(final String str) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BasicActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

    }

             @Override
             public void onBackPressed() {
               //  super.onBackPressed();
                 super.finish();
             }

             @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
