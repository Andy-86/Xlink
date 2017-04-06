package com.example.andy.connectutil.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 95815 .
 * Date:2017/3/30.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public abstract class BaseFragment extends Fragment  {

     private View rootview;
    protected   HolderListener holderListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //    return super.onCreateView(inflater, container, savedInstanceState);
        if (rootview != null) {
            return rootview;
        } else {
            rootview = inflater.inflate(getFragmentLayoutId(), container, false);

        }
        initView(rootview);
        setListener();
        initData();
        showLog("onCreateView");
        return rootview;
    }

protected void showLog(String str){
    Log.d("waiwen",this.getTag()+": "+str);

}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog("onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        showLog("onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        showLog("onResume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof HolderListener) {
            holderListener = (HolderListener) getActivity();
            showLog("取得接口实现类");
        }
    }

    /**
     * @return 获取该Fragment关联的View；
     */
    public abstract int getFragmentLayoutId();

    /**
     * @param view
     */
    public abstract void initView(View view);
    public abstract void setListener();
    public abstract void initData();

    /**
     * 作用：获取控件 Id (避免类型转换的繁琐，抽取 findViewById() )
     *
     * @param view 父View
     * @param ReId 控件的ID
     * @param <T>  具体控件View
     * @return
     */
    public <T extends View> T obtainView(View view,int ReId) {
        return (T) view.findViewById(ReId);
    }

}
