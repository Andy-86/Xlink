package com.example.andy.connectutil.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andy.connectutil.R;

import java.util.List;

import com.example.andy.connectutil.Bean.HelpItem;

/**
 * Created by 95815 .
 * Date:2017/4/4.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {

    int opened = -1; // 列表展开标识

    List<HelpItem> helpItemList;
    Context context;
    private LayoutInflater mInflater;

    public HelpAdapter(Context context, List<HelpItem> list) {
        super();
        this.helpItemList = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public HelpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return null;
        View itemView = mInflater.inflate(R.layout.recycleview_item_help, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(HelpAdapter.MyViewHolder holder, int position) {
          HelpItem helpItem = helpItemList.get(position);
          holder.tv_helpName.setText(helpItem.getHelpName());
          holder.bindItem(position,helpItem.getHelpInfo());

    }

    @Override
    public int getItemCount() {
        if (helpItemList.size() != 0) {
            return helpItemList.size();
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_helpName;
        public TextView tv_helpInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_helpName = (TextView) itemView.findViewById(R.id.item_help_tv);
            tv_helpInfo = (TextView) itemView.findViewById(R.id.item_help_info);
           itemView.setOnClickListener(this);
        }

        public void bindItem(int pos,String str){
            tv_helpInfo.setText(str);
            if (pos == opened)
                tv_helpInfo.setVisibility(View.VISIBLE);
            else
                tv_helpInfo.setVisibility(View.GONE);

        }


        @Override
        public void onClick(View v) {
            if (opened ==getPosition()) {
                opened = -1;
                notifyItemChanged(getPosition());
            }
            else {
                int oldOpened = opened;
                opened = getPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
            }

        }
    }


}
