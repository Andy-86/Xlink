package com.example.andy.connectutil.Fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.example.andy.connectutil.Activity.FanLightHelper;
import com.example.andy.connectutil.R;
import com.example.andy.connectutil.View.ControlView;
import com.example.andy.connectutil.entity.Device.FanLinght;

import io.xlink.wifi.sdk.XlinkCode;


/**
 * Created by 95815 on 2017/3/25.
 */

public class ContFanLedFragment extends Fragment {


    private boolean pop_state1 =true;
    private boolean pop_state2 =true;
    private boolean pop_state3 =true;
    private boolean pop_state4 =true;

    private boolean state = false;
    private  byte date=1;
    private ControlView controlView;
    private ControlView.OnControlListener listener;
    private PopupWindow popupWindow1,popupWindow2,popupWindow3,popupWindow4;
    private FanLightHelper fanLightHelper;
    FanLinght fanLinght;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);




          fanLightHelper=new FanLightHelper();
          fanLightHelper.getDevice();
          fanLinght=fanLightHelper.update();
          initData();//初始化数据值

          View view = inflater.inflate(R.layout.fragment_view_fanled,container,false);
          controlView = (ControlView) view.findViewById(R.id.controlView);
          controlView.setGeerNum(4);
          controlView.setBottomAngle(50f);
          popupWindow1 = getPopupWindow(R.layout.popview_itbn_one);
          popupWindow2 = getPopupWindow(R.layout.popview_itbn_two);
          popupWindow3 = getPopupWindow(R.layout.popview_itbn_three);
          popupWindow4 =getPopupWindow(R.layout.popview_itbn_four);
          controlView.setOncontrolListener(new ControlView.OnControlListener() {
            @Override
            public void onClickCenter() {
                 fanLightHelper.setDataPoint(0, XlinkCode.DP_TYPE_BOOL,true);
            }

            @Override
            public void onClickBottom() {

            }

            @Override
            public void onClickOne() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,date);
            }

            @Override
            public void onClickTwo() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,date);
            }

            @Override
            public void onClickThree() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,date);
            }

            @Override
            public void onClickFour() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,date);
            }

            @Override
            public void onClickFive() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,5);
            }

            @Override
            public void onClickSix() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,6);
            }

            @Override
            public void onClickSeven() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,7);
            }

            @Override
            public void onClickEight() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,8);
            }

            @Override
            public void onClickNine() {
                fanLightHelper.setDataPoint(6, XlinkCode.DP_TYPE_BYTE,9);
            }
        });
        ImageButton imageButton1 = (ImageButton)view.findViewById(R.id.ibtn_one_top);
      imageButton1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           if(!pop_state1) {popupWindow1.setWidth(v.getMeasuredWidth());
              popupWindow1.showAsDropDown(v,0,15);
           pop_state1 = true;}
              else {
               popupWindow1.dismiss();
               pop_state1 = false;
           }
          }
      });
        ImageButton imageButton2 = (ImageButton)view.findViewById(R.id.ibtn_two_top);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pop_state2) {popupWindow2.setWidth(v.getMeasuredWidth());
                    popupWindow2.showAsDropDown(v,0,15);
                    pop_state2 = true;}
                else {
                    popupWindow2.dismiss();
                    pop_state2 = false;
                }
            }
        });
        ImageButton imageButton3 = (ImageButton)view.findViewById(R.id.ibtn_three_top);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pop_state3) {popupWindow3.setWidth(v.getMeasuredWidth());
                    popupWindow3.showAsDropDown(v,0,15);
                    pop_state3 = true;}
                else {
                    popupWindow3.dismiss();
                    pop_state3 = false;
                }
            }
        });
        ImageButton imageButton4 = (ImageButton)view.findViewById(R.id.ibtn_four_top);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pop_state4) {popupWindow4.setWidth(v.getMeasuredWidth());
                    popupWindow4.showAsDropDown(v,0,15);
                    pop_state4 = true;}
                else {
                    popupWindow4.dismiss();
                    pop_state4 = false;
                }
            }
        });
        return view;
    }

    private void initData() {

    }

    public PopupWindow getPopupWindow(int LayoutId) {
        PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        View v = LayoutInflater.from(getContext()).inflate(LayoutId, null);
        popupWindow.setContentView(v);
        return popupWindow;
    }
}
