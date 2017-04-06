package com.example.andy.connectutil.Activity;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.andy.connectutil.Adapter.HelpAdapter;
import com.example.andy.connectutil.Bean.HelpItem;
import com.example.andy.connectutil.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95815 .
 * Date:2017/4/4.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class HelpActivity extends BarActivity {

    private List<HelpItem> helpItemList;
    private RecyclerView recyclerHelp;
    private HelpAdapter helpAdapter;

    @Override
    protected void setBefortLayout() {
        super.setBefortLayout();
        helpItemList = new ArrayList<>();
        helpItemList.add(new HelpItem("设备列表", "设备列表设备列表设备列表"));
        helpItemList.add(new HelpItem("风扇灯", "设备列表设备列表设备列表" +
                "设备列表设备列表设备列表"
        ));
        helpItemList.add(new HelpItem("灯", "设备列表设备列表设备列表"));
        helpItemList.add(new HelpItem("LED", "设备列表设备列表设备列表"));
        helpItemList.add(new HelpItem("浴霸", "设备列表设备列表设备列表"));
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerHelp = obtainView(R.id.recycleview_help);
        helpAdapter = new HelpAdapter(this, helpItemList);
// 实现RecyclerView实现竖向列表展示模式
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerHelp.setLayoutManager(layoutManager);
        recyclerHelp.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerHelp.setAdapter(helpAdapter);

    }

    protected void initData() {

        super.initData();
        getTvTitle().setText("帮助");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }
}