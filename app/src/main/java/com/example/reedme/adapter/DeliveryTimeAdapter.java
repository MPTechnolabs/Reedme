package com.example.reedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.model.DeliveryTime;

import java.util.List;

public class DeliveryTimeAdapter extends BaseAdapter {
    List<DeliveryTime> DeliveryTimeList;
    public Context context;

    public class ViewHolder {
        TextView TimeValue;
    }

    public DeliveryTimeAdapter(List<DeliveryTime> listValue, Context context) {
        this.context = context;
        this.DeliveryTimeList = listValue;
    }

    public int getCount() {
        return this.DeliveryTimeList.size();
    }

    public Object getItem(int position) {
        return this.DeliveryTimeList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_area_item, parent, false);
            viewHolder.TimeValue = (TextView) convertView.findViewById(R.id.txt_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.TimeValue.setText(this.DeliveryTimeList.get(position).getDeliveryValue());
        return convertView;
    }
}
