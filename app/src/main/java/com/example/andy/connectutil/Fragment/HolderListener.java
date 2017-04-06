package com.example.andy.connectutil.Fragment;

/**
 * Created by 95815 on 2017/3/10.
 * MainActivity实现该接口，将接口实现传到FragmentHolder中，用于回调界面视图变化。
 * mvc模式
 *
 */

public interface HolderListener {

    /**
     * 主页面界面视图,当启动该fragment时就会回调该接口，进而显示相应标题。
     */
    void setMainPage(String fragment_title, int view_status);

    void startWifiConnection(String str);

    void startCountdownFragment();

}
