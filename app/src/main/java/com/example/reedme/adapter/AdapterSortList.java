package com.example.reedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.reedme.R;

import java.util.ArrayList;

public class AdapterSortList extends BaseAdapter {
    ArrayList<String> myList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public AdapterSortList(Context context, ArrayList<String> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }


    @Override
    public String  getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_sort, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        //  ListData currentListData = getItem(position);
        mViewHolder.tvTitle.setText(myList.get(position));
        // mViewHolder.ivIcon.setImageResource(currentListData.getImageOfHomeFeed());
        return convertView;
    }
    private class MyViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.txt_title);


        }
    }
}
