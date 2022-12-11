package com.pjcgnu.chatterbox;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<StatsModel> viewDatas;


    public StatsAdapter(Context context, ArrayList<StatsModel> data) {
        mContext = context;
        viewDatas = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return viewDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public StatsModel getItem(int position) {
        return viewDatas.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item_stats, null);

        TextView tv_name = (TextView)view.findViewById(R.id.stats_name);
        TextView tv_s_time = (TextView)view.findViewById(R.id.stats_s_time);
        TextView tv_e_time = (TextView)view.findViewById(R.id.stats_e_time);
        TextView tv_t_time = (TextView) view.findViewById(R.id.stats_t_time);

        tv_name.setText(viewDatas.get(position).getReadingName());
        tv_s_time.setText(viewDatas.get(position).getReadingSTime());
        tv_e_time.setText(viewDatas.get(position).getReadingETime());
        tv_t_time.setText(viewDatas.get(position).getReadingTTime());

        return view;
    }
}
