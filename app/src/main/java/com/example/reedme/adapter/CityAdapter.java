package com.example.reedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.model.GetCityNameDetail;
import com.example.reedme.model.GetStateNameDetail;

import java.util.List;

/**
 * Created by Jolly Raiyani on 4/15/2016.
 */
public class CityAdapter  extends BaseAdapter {
    static final /* synthetic */ boolean $assertionsDisabled;
    GetCityNameDetail categoryItem;
    Context context;
    List<GetCityNameDetail> itemList;

    static class ViewHolder {
        TextView id;
        TextView itemName;

        ViewHolder() {
        }
    }

    static {
        $assertionsDisabled = !CategoryListAdapter.class.desiredAssertionStatus();
    }

    public CityAdapter(Context context, List<GetCityNameDetail> listItems) {
        this.context = context;
        this.itemList = listItems;
    }

    public int getCount() {
        return this.itemList.size();
    }

    public Object getItem(int position) {
        return this.itemList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

            view = layoutInflater.inflate(R.layout.list_select_heard_list_dialoug, null);

            holder = new ViewHolder();
            if ($assertionsDisabled || view != null) {
                holder.id = (TextView) view.findViewById(R.id.txt_id);
                holder.itemName = (TextView) view.findViewById(R.id.txt_app_info_name);

                view.setTag(holder);
            } else {
                throw new AssertionError();
            }
        }
        holder = (ViewHolder) view.getTag();
        this.categoryItem = this.itemList.get(position);
        holder.id.setText(this.categoryItem.getId());
        holder.itemName.setText(this.categoryItem.getName());


        return view;
    }
}
