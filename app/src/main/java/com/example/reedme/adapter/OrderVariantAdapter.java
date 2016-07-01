package com.example.reedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.model.OrderVariant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderVariantAdapter extends BaseAdapter {
    Context context;
    List<OrderVariant> orderVariantList;

    static class ViewHolder {
        TextView TotalAmount;
        TextView VariantName;
        TextView VariantQty;
        ImageView imageView;
        OrderVariant orderVariant;

        ViewHolder() {
        }
    }

    public OrderVariantAdapter(Context context, List<OrderVariant> listItems) {
        this.context = context;
        this.orderVariantList = listItems;
    }

    public int getCount() {
        return this.orderVariantList.size();
    }

    public Object getItem(int position) {
        return this.orderVariantList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.order_variantes_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.img_category_sub_item);
            holder.VariantName = (TextView) view.findViewById(R.id.txt_order_detail_item_name);
            holder.VariantQty = (TextView) view.findViewById(R.id.txt_order_detail_qty);
            holder.TotalAmount = (TextView) view.findViewById(R.id.txt_order_detail_total_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.orderVariant = this.orderVariantList.get(position);
        //float pAmount = Float.parseFloat(holder.orderVariant.getVantagePrice()) * ((float) Integer.parseInt(holder.orderVariant.getVantageQty()));
        holder.VariantName.setText(holder.orderVariant.getVantageName());
        holder.VariantQty.setText("Quantity :" +holder.orderVariant.getVantageQty());
        holder.TotalAmount.setText("Amount :" +holder.orderVariant.getVantagePrice());

        if (holder.orderVariant.getImage().length() > 0) {
            Picasso.with(StartActivity.context).load(holder.orderVariant.getImage()).placeholder((int) R.mipmap.logo).into(holder.imageView);
        }
        return view;
    }
}
