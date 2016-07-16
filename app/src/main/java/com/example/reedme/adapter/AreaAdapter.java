package com.example.reedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.model.GetCountryNameDetail;

import java.util.ArrayList;

/**
 * Created by jolly on 15/7/16.
 */
public class AreaAdapter extends BaseAdapter {

    ArrayList areaList = new ArrayList();
    LayoutInflater inflater;
    Context context;


    public AreaAdapter(Context context, ArrayList areaList) {
        this.areaList = areaList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return areaList.size();
    }

    @Override
    public GetCountryNameDetail getItem(int position) {
        return (GetCountryNameDetail) areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_select_heard_list_dialoug, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        GetCountryNameDetail currentListData = getItem(position);

        mViewHolder.tvName.setText(currentListData.getName());

        return convertView;
    }

    private class MyViewHolder {
        TextView  tvName;

        public MyViewHolder(View item) {
            tvName = (TextView) item.findViewById(R.id.txt_app_info_name);
        }
    }
}