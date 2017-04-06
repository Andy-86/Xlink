package com.example.andy.connectutil.Activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.andy.connectutil.R;


/**
 * Created by 95815 .
 * Date:2017/4/4.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class BarActivity extends BasicActivity implements View.OnClickListener {

   private TextView tv_title;
   private ImageButton ibtn_backup;


    @Override
    protected void setBefortLayout() {

    }

    @Override
    protected void setActionbar() {
        tv_title = obtainView(R.id.tv_toolbar_others);
        ibtn_backup = obtainView(R.id.ibtn_toolbar_backup);
        ibtn_backup.setOnClickListener(this);
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ibtn_toolbar_backup){
            this.finish();
        }
    }

  public TextView getTvTitle(){
        return tv_title;
    }
   public ImageButton getIbtn_backup(){
        return ibtn_backup;
    }

}
