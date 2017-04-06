package com.example.andy.connectutil.Fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.andy.connectutil.R;
import com.example.andy.connectutil.entity.Net.Content;

/**
 * Created by 95815 on 2017/3/10.
 */

public class EquitmentSelectFragment extends BaseFragment implements View.OnClickListener {


    public static final String fragment_tag = "EquitmentSelectFragment";


    private Button btn_fanLed;
    private Button btn_open;
    private Button btn_LED;
    private Button btn_bath;

    public static EquitmentSelectFragment newInstance() {

        Bundle args = new Bundle();
        EquitmentSelectFragment fragment = new EquitmentSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getFragmentLayoutId() {
        return R.layout.view_equitment_select;
    }

    @Override
    public void initView(View view) {

        btn_bath = (Button) view.findViewById(R.id.select_btn_bath);
        btn_fanLed = (Button) view.findViewById(R.id.select_btn_fanled);
        btn_open = (Button) view.findViewById(R.id.select_btn_open);
        btn_LED = (Button) view.findViewById(R.id.select_btn_led);

    }

    @Override
    public void setListener() {

        btn_bath.setOnClickListener(EquitmentSelectFragment.this);
        btn_LED.setOnClickListener(EquitmentSelectFragment.this);
        btn_open.setOnClickListener(EquitmentSelectFragment.this);
        btn_fanLed.setOnClickListener(EquitmentSelectFragment.this);

    }

    @Override
    public void initData() {
        //  holderListener.setMainPage(getString(R.string.select_equitment),View.VISIBLE);
    }


    @Override
    public <T extends View> T obtainView(View view, int ReId) {
        return super.obtainView(view, ReId);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_btn_fanled:
                holderListener.startWifiConnection(Content.FanLIght_ID);
                break;
            case R.id.select_btn_open:
                holderListener.startWifiConnection(Content.Light_ID);
                break;
            case R.id.select_btn_led:
                holderListener.startWifiConnection(Content.LEDLIght_ID);
                break;
            case R.id.select_btn_bath:
                holderListener.startWifiConnection(Content.BathBully_ID);
                break;

        }
    }

}
