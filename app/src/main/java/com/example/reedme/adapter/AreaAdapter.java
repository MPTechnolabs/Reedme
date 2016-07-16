package com.example.reedme.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.model.GetAgeDetail;
import com.example.reedme.model.GetCountryNameDetail;

import java.util.ArrayList;

/**
 * Created by jolly on 15/7/16.
 */
public class AreaAdapter extends BaseAdapter {

    ArrayList ageList = new ArrayList();
    LayoutInflater inflater;
    Context context;


    public AreaAdapter(Context context, ArrayList myList) {
        this.ageList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return ageList.size();
    }

    @Override
    public GetAgeDetail getItem(int position) {
        return (GetAgeDetail) ageList.get(position);
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

        GetAgeDetail currentListData = getItem(position);

        mViewHolder.tvId.setText(currentListData.getId());
        mViewHolder.tvName.setText(currentListData.getName());

        return convertView;
    }

    private class MyViewHolder {
        TextView tvId, tvName;

        public MyViewHolder(View item) {
            tvId = (TextView) item.findViewById(R.id.txt_id);
            tvName = (TextView) item.findViewById(R.id.txt_app_info_name);
        }
    }
}