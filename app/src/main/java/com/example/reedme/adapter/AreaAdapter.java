package com.example.reedme.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.model.GetAgeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jolly on 15/7/16.
 */
public class AreaAdapter extends BaseAdapter {

    List<GetAgeDetail> rowItems;
    Context context;


    public AreaAdapter(Context context, List<GetAgeDetail> items) {
        this.rowItems = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_select_heard_list_dialoug, null);
            holder = new ViewHolder();
            holder.tvId = (TextView) convertView.findViewById(R.id.txt_id);
            holder.tvName = (TextView) convertView.findViewById(R.id.txt_app_info_name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetAgeDetail rowItem = (GetAgeDetail) getItem(position);

        holder.tvId.setText(rowItem.getId());
        holder.tvName.setText(rowItem.getName());

        return convertView;
    }
    private class ViewHolder {
        TextView tvId, tvName;


    }
}