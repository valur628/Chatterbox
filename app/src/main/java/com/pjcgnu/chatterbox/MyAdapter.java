package com.pjcgnu.chatterbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<MainViewModel> viewDatas;

    public MyAdapter(Context context, ArrayList<MainViewModel> data) {
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
    public MainViewModel getItem(int position) {
        return viewDatas.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_custom, null);

        TextView bookNames = (TextView)view.findViewById(R.id.name_tv);
        ImageView bookImages = (ImageView)view.findViewById(R.id.book_image);
        TextView bookWriters = (TextView)view.findViewById(R.id.writer_tv);
        RatingBar bookRatings = (RatingBar) view.findViewById(R.id.ratingBar_five);

        bookNames.setText(viewDatas.get(position).getBookName());
        bookImages.setImageResource(Integer.parseInt(viewDatas.get(position).getBookImage()));
        bookWriters.setText(viewDatas.get(position).getBookWriter());
        bookRatings.setRating(viewDatas.get(position).getBookRating());

        return view;
    }
}