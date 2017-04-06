package com.example.andy.connectutil.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.andy.connectutil.Adapter.AddEquitAdapter;
import com.example.andy.connectutil.Bean.Equitment;
import com.example.andy.connectutil.Fragment.CountDownFragment;
import com.example.andy.connectutil.Fragment.EquitmentSelectFragment;
import com.example.andy.connectutil.Fragment.FragmentHolder;
import com.example.andy.connectutil.Fragment.HolderListener;
import com.example.andy.connectutil.Fragment.WifiConnectionFragment;
import com.example.andy.connectutil.R;
import com.example.andy.connectutil.SharePrefrence.Account;
import com.example.andy.connectutil.View.SpaceItemDecoration;
import com.example.andy.connectutil.entity.WifiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95815 .
 * Date:2017/3/29.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 * 描述：该活动是app的主活动，管理view和维护fragment
 */

public class MainActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,HolderListener {


    private Account account;
    private FragmentManager fragmentManager;

    public FragmentHolder holder;
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    protected RelativeLayout rl_bottom;

    List<Equitment> equitmentList;
    RecyclerView recyclerView;
    AddEquitAdapter mAdapter;

    EditText et_tb_search;
    ImageView ibtn_tb_search;

    ImageButton toolbar_search;
    ImageButton toolbar_menu;
    ImageButton img_backup;

    ImageButton bottom_add_equitment;
    ImageButton bottom_setting;

    TextView main_title;

    protected BottomSheetBehavior behavior;

    @Override
    protected void setBefortLayout() {

    }

    @Override
    protected void setActionbar() {
        Toolbar toolbar = obtainView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {
        //测试数据
        equitmentList.add(new Equitment("风扇", R.drawable.button_menu_fanc));
        equitmentList.add(new Equitment("LED灯",R.drawable.button_menu_led));
        mAdapter = new AddEquitAdapter(this, equitmentList);
        int spacingInPixels = 8;
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        Log.d("waiwen","setAdapter：");

         fragmentManager = getSupportFragmentManager();
         holder = new FragmentHolder(this,fragmentManager);
         holder.setState(FragmentHolder.MAIN_PAGE);


        account=new Account(getApplicationContext());

    }

    @Override
    protected void setListener() {

        toolbar_menu.setOnClickListener(this);
        toolbar_search.setOnClickListener(this);
        img_backup.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(this);
        rl_bottom.setOnClickListener(this);

        bottom_add_equitment.setOnClickListener(this);
        bottom_setting.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        toolbar_search = obtainView(R.id.toolbar_ibtn_search);
        toolbar_menu = obtainView(R.id.toolbar_menu);

        img_backup = obtainView(R.id.img_backup);
        main_title = obtainView(R.id.main_tv_title);

        navigationView = obtainView(R.id.nav_view);
        rl_bottom = obtainView(R.id.rl_bottom_addequit);
        drawer = obtainView(R.id.drawer_layout);

        NestedScrollView bottomSheetView = obtainView(R.id.bottomsheet_view);
        behavior = BottomSheetBehavior.from(bottomSheetView);

        et_tb_search = obtainView(R.id.toolbar_et_search);
        ibtn_tb_search = obtainView(R.id.toolbar_ibtn_search);

        bottom_add_equitment = obtainView(R.id.bottom_add_ibtn);
        bottom_setting = obtainView(R.id.bottom_setting);

        recyclerView = obtainView(R.id.pop_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        equitmentList = new ArrayList<>();
         Log.d("waiwen","initview");
       // recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                setDrawerOnOff();
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.rl_bottom_addequit:
                showToast("点击了底部菜单");
                setBottomSheetOnOff();
                break;
            case R.id.bottom_add_ibtn:
                if(holder.getState() == FragmentHolder.MAIN_PAGE){
                holder.addFragment(EquitmentSelectFragment.newInstance(),EquitmentSelectFragment.fragment_tag);
                holder.setState(FragmentHolder.SELECT_FRAGMENT);}
                if(holder.getState() != FragmentHolder.SELECT_FRAGMENT){
                    holder.replaceFragment(EquitmentSelectFragment.newInstance(),EquitmentSelectFragment.fragment_tag);
                    holder.setState(FragmentHolder.SELECT_FRAGMENT);
                }


                setBottomSheetOnOff();
                break;
            case R.id.img_backup:
                if(holder.getState() == FragmentHolder.WIFI_CONNECTION_FRAGMENT){
                             holder.removeFragmentByTag(WifiConnectionFragment.fragment_tag);
                }
                else if(holder.getState() == FragmentHolder.SELECT_FRAGMENT){
                    holder.removeFragmentByTag(EquitmentSelectFragment.fragment_tag);
                }
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_addequitment) {
            add_Aquitment();
            setDrawerOnOff();
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, ShareActivity.class));
            setDrawerOnOff();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            setDrawerOnOff();
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(this, HelpActivity.class));
            setDrawerOnOff();
        } else if (id == R.id.nav_language) {
            startActivity(new Intent(this,LanguageActivity.class));
            setDrawerOnOff();
        }
        else if (id == R.id.nav_backup) {
            account.setAccount(account.getUser(),"");
            finish();
            Intent intent=new Intent(MainActivity.this,RegisterAndLoginActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void add_Aquitment() {
        if (holder.getState() == FragmentHolder.MAIN_PAGE) {
            holder.addFragment(EquitmentSelectFragment.newInstance(), EquitmentSelectFragment.fragment_tag);
            holder.setState(FragmentHolder.SELECT_FRAGMENT);
        }
        if (holder.getState() != FragmentHolder.SELECT_FRAGMENT) {
            holder.replaceFragment(EquitmentSelectFragment.newInstance(), EquitmentSelectFragment.fragment_tag);
            holder.setState(FragmentHolder.SELECT_FRAGMENT);
        }


    }


    protected void setDrawerOnOff() {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
        } else {
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    @Override
    public void startCountdownFragment() {
        if(holder.getState() == FragmentHolder.WIFI_CONNECTION_FRAGMENT){
            holder.replaceFragment(CountDownFragment.newInstance(),CountDownFragment.fragment_tag);
        }

    }


    @Override
    public void startWifiConnection(String produt_id) {

        if(holder.getState() == FragmentHolder.SELECT_FRAGMENT){
           holder.replaceFragment(WifiConnectionFragment.newInstance(WifiUtils.getWifiSSID(this),produt_id),WifiConnectionFragment.fragment_tag);
           holder.setState(FragmentHolder.WIFI_CONNECTION_FRAGMENT);
        }
    }

    /**
     * @param fragment_title  页面标题
     * @param view_status  后退键的状态
     */
    @Override
    public void setMainPage(String fragment_title, int view_status) {
        img_backup.setVisibility(view_status);
        main_title.setText(fragment_title);

    }



    protected void setBottomSheetOnOff() {
        int state = behavior.getState();
        if (state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   public FragmentHolder getHolder()
   {
       return holder;
   }
}
