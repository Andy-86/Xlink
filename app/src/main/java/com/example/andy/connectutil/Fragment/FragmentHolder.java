package com.example.andy.connectutil.Fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.andy.connectutil.R;


/**
 * Created by 95815 on 2017/3/10.
 * 该类用于管理Fragment,以及回调界面视图变化
 *
 * 目前网上有更好的fragment管理实现方法，将可以考虑替换，
 *
 */

public class FragmentHolder {


    private HolderListener holderListener;
    private FragmentManager manager;

    private int state = 0;
    public static final int MAIN_PAGE = 0;
    public  static final int SELECT_FRAGMENT = 1;
    public static final int WIFI_CONNECTION_FRAGMENT = 2;


    /**
     * @param state 记录当前处于哪一个Fragment，需要改进，目前能记录的状态为主页面，选择设备页面，
     */
    public void setState(int state) {
        this.state = state;
        if(state == MAIN_PAGE){
            holderListener.setMainPage("主界面", View.INVISIBLE);
        }else if(state == SELECT_FRAGMENT){
            holderListener.setMainPage("选择设备列表", View.VISIBLE);
        }else if(state == WIFI_CONNECTION_FRAGMENT){
            holderListener.setMainPage("设备配网", View.VISIBLE);
        }

    }
    public void onBackupStatus(){


    }


    public int getState() {
        return state;
    }

    /**
     * @param holderListener 主活动实例化该类和获得的FragmentManager时将接口实现类传递进来
     * @param manager
     */
    public FragmentHolder(HolderListener holderListener, FragmentManager manager) {
        super();
        this.holderListener = holderListener;
        this.manager = manager;
    }

    /**
     * @param fragment 添加fragment
     */
    public void addFragment(Fragment fragment,String tag) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_layout,fragment,tag);
            transaction.commit();
    }

    /**
     * @param tag 移除fragment
     */
    public void removeFragmentByTag(String tag) {
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = manager.findFragmentByTag(tag);
        if(fragment != null){
            transaction.remove(fragment);
            transaction.commit();
            setState(0);}
    }

    /**
     * @param frament 替换fragment
     */
    public void replaceFragment(Fragment frament,String tag) {

        FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_layout, frament,tag);
            transaction.addToBackStack(null);
            transaction.commit();
    }



}
