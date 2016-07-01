package com.example.reedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.model.OrderDetail;

import java.util.List;

public class OrderItemAdapter extends BaseAdapter {
    Context context;
    List<OrderDetail> orderDetailList;

    static class ViewHolder {
        TextView OrderAmount;
        TextView OrderDate;
        TextView OrderId;
        OrderDetail orderDetail;
        TextView txtCanceled;
        TextView txtDelivered;
        TextView txtDelivery;
        TextView txtPacked;
        View view;

        ViewHolder() {
        }
    }

    public OrderItemAdapter(Context context, List<OrderDetail> listItems) {
        this.context = context;
        this.orderDetailList = listItems;
    }

    public int getCount() {
        return this.orderDetailList.size();
    }

    public Object getItem(int position) {
        return this.orderDetailList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.my_order_item, parent, false);
            holder = new ViewHolder();
            holder.OrderId = (TextView) view.findViewById(R.id.txt_order_id_value);
            holder.OrderDate = (TextView) view.findViewById(R.id.txt_order_date_value);
            holder.OrderAmount = (TextView) view.findViewById(R.id.txt_order_amount_value);
            holder.txtPacked = (TextView) view.findViewById(R.id.txt_order_packed);
            holder.txtDelivery = (TextView) view.findViewById(R.id.txt_order_delivery);
            holder.txtDelivered = (TextView) view.findViewById(R.id.txt_order_delivered);
            holder.txtCanceled = (TextView) view.findViewById(R.id.txt_order_canceled);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.orderDetail = this.orderDetailList.get(position);
        holder.OrderId.setText(holder.orderDetail.getOrderId());
        holder.OrderDate.setText(holder.orderDetail.getOrderDate());
        holder.OrderAmount.setText(holder.orderDetail.getOrderAmount());
        return view;

    }
}
