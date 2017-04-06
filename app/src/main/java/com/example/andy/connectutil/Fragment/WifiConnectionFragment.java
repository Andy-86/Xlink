package com.example.andy.connectutil.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andy.connectutil.Activity.MainActivity;
import com.example.andy.connectutil.R;


/**
 * Created by 95815 on 2017/3/10.
 */

public class WifiConnectionFragment extends BaseFragment implements View.OnClickListener {


    public static final String fragment_tag = "WifiConnectionFragment";

    private static final String TAG = "waiwen";

    private boolean password_status = true;
    private TextView wifi_name;
    private EditText et_wifi_password;
    private Button btn_next;
    private TextView tv_firstguide;
    private ImageButton ibtn_password_see;
    private LinearLayout linearLayout_firstguide;
    private static String product_ID;

    public static WifiConnectionFragment newInstance(String wifiName, String produt_id) {

        Bundle args = new Bundle();
        args.putString("wifi名称", wifiName);
        WifiConnectionFragment fragment = new WifiConnectionFragment();
        fragment.setArguments(args);
        product_ID = produt_id;
        return fragment;
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_wifi_connection;
    }

    @Override
    public void initView(View view) {
        showLog("initview");
        wifi_name = obtainView(view, R.id.tv_wifi_num);
        linearLayout_firstguide =(LinearLayout) view.findViewById(R.id.view_new_guide);
        btn_next = obtainView(view, R.id.btn_next);
        tv_firstguide = obtainView(view, R.id.tv_first_guide);
        ibtn_password_see = obtainView(view, R.id.ibtn_password_see);
        et_wifi_password = obtainView(view, R.id.et_wifi_password);

    }

    @Override
    public void setListener() {
        btn_next.setOnClickListener(this);
        //用于新手引导层
       linearLayout_firstguide.setOnClickListener(this);
        //设置透明度
        linearLayout_firstguide.getBackground().setAlpha(100);
        ibtn_password_see.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle args = getArguments();
        String str = args.getString("wifi名称"); //拿到传送过来的数据
      tv_firstguide.setText("wifi配对界面时输入WiFi密码然后用遥控激活配网：按一下遥控开关键再按一下灯开关键，正确配对后\n" +
               "出现‘滴’的一声。" + "\n" + "不带遥控激活方式：按墙壁开关键5次即配对成功。" + "\n" + "再按“next”键");
        wifi_name.setText(str);
    }

    @Override
    public <T extends View> T obtainView(View view, int ReId) {
        return super.obtainView(view, ReId);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach:关联 ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
//               /* String password=et_wifi_password.getText().toString();
//                 WiFiConfig wiFiConfig=new WiFiConfig(getActivity());
//                 wiFiConfig.StartConfig(password);
//                  holderListener.startCountdownFragment();*/
                //获取FargmentHolder跳转到DeviceFargement
                MainActivity mainActivity = (MainActivity) getActivity();
                FragmentHolder holder = mainActivity.getHolder();
                DeviceFragement deviceFragement = DeviceFragement.newInstance(product_ID);
                holder.replaceFragment(deviceFragement, DeviceFragement.TAG);
                break;
           case R.id.view_new_guide:
                //点击即消失
                linearLayout_firstguide.setVisibility(View.INVISIBLE);
               break;
            case R.id.ibtn_password_see:
                if (password_status) {
                    //密码隐藏
                    et_wifi_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password_status = false;
                    ibtn_password_see.setImageResource(R.drawable.password_eye_white);
                } else {
                    //密码可见
                    et_wifi_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password_status = true;
                    ibtn_password_see.setImageResource(R.drawable.password_eye_orange);
                }
               break;
        }
    }


}
