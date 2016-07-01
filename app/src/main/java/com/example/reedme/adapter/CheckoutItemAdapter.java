package com.example.reedme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.activity.CheckoutContinueActivity;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.helper.Util;
import com.example.reedme.model.CheckOutVantage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckoutItemAdapter extends BaseAdapter {
    List<CheckOutVantage> vantageList;

    /* renamed from: com.vegies.app.adapter.CheckoutItemAdapter.1 */
    class C05071 implements OnClickListener {
        private final /* synthetic */ ViewHolder val$holder;

        C05071(ViewHolder viewHolder) {
            this.val$holder = viewHolder;
        }

        public void onClick(View v) {
            if (this.val$holder.ItemCount > 0) {
                ViewHolder viewHolder = this.val$holder;
                viewHolder.ItemCount--;
                this.val$holder.CountText.setText(String.valueOf(this.val$holder.ItemCount));
                Util.getInstance(StartActivity.context).RemoveCheckOutVantage(this.val$holder.variant.getVantageId());
                this.val$holder.Count.setText(" x " + String.valueOf(this.val$holder.ItemCount) + " = ");
                this.val$holder.CountText.setText(String.valueOf(this.val$holder.ItemCount));
                String value = val$holder.variant.getVantagePrice();
                /*value = value.substring(1);
                String result = value.replaceAll("[.,]","");
*/
                this.val$holder.TotalAmount.setText(String.valueOf(Float.parseFloat(value) * ((float) this.val$holder.ItemCount)));
                CheckoutContinueActivity.setCheckOutPayableData();
                if (this.val$holder.ItemCount == 0) {
                    CheckoutItemAdapter.this.vantageList.remove(this.val$holder.Position);
                    CheckoutItemAdapter adapter = (CheckoutItemAdapter) CheckoutContinueActivity.checkoutList.getAdapter();
                    adapter.notifyDataSetInvalidated();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    class C05082 implements OnClickListener {
        private final /* synthetic */ ViewHolder val$holder;

        C05082(ViewHolder viewHolder) {
            this.val$holder = viewHolder;
        }

        public void onClick(View v) {
            ViewHolder viewHolder = this.val$holder;
            viewHolder.ItemCount++;
            this.val$holder.CountText.setText(String.valueOf(this.val$holder.ItemCount));
            Util.getInstance(StartActivity.context).AddCheckOutItem(this.val$holder.variant);
            this.val$holder.Count.setText(" x " + String.valueOf(this.val$holder.ItemCount) + " = ");
            this.val$holder.CountText.setText(String.valueOf(this.val$holder.ItemCount));

            String value = val$holder.variant.getVantagePrice();
 /*           value = value.substring(1);
            String result = value.replaceAll("[.,]","");*/

            this.val$holder.TotalAmount.setText(String.valueOf(Float.parseFloat(value) * ((float) this.val$holder.ItemCount)));
            CheckoutContinueActivity.setCheckOutPayableData();
        }
    }

    static class ViewHolder {
        TextView Count;
        TextView CountText;
        ImageView Image;
        int ItemCount;
        TextView Name;
        int Position;
        TextView Price;
        TextView Quantity;
        TextView TotalAmount;
        RelativeLayout lytItemMinus;
        RelativeLayout lytItemPlus;
        CheckOutVantage variant;

        ViewHolder() {
        }
    }

    public CheckoutItemAdapter(HashMap<Integer, CheckOutVantage> checkOutVantageList) {
        this.vantageList = new ArrayList(checkOutVantageList.values());
    }

    public int getCount() {
        return this.vantageList.size();
    }

    public Object getItem(int position) {
        return this.vantageList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(StartActivity.context).inflate(R.layout.checkout_sub_item, null);
            holder = new ViewHolder();
            holder.Image = (ImageView) view.findViewById(R.id.img_checkout_continute_item);
            holder.Name = (TextView) view.findViewById(R.id.txt_checkout_continue_item_name);
            holder.Quantity = (TextView) view.findViewById(R.id.txt_checkout_continue_item_quantity);
            holder.Price = (TextView) view.findViewById(R.id.txt_checkout_continue_item_price);
            holder.Count = (TextView) view.findViewById(R.id.txt_checkout_continue_item_count);
            holder.TotalAmount = (TextView) view.findViewById(R.id.txt_checkout_continue_total_price);
            holder.CountText = (TextView) view.findViewById(R.id.txt_checkout_item_count);
            holder.lytItemMinus = (RelativeLayout) view.findViewById(R.id.lyt_checkout_item_minus);
            holder.lytItemPlus = (RelativeLayout) view.findViewById(R.id.lyt_checkout_item_plus);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.Position = position;
        holder.variant = this.vantageList.get(position);
        holder.ItemCount = holder.variant.getVantageQty();
        holder.Name.setText(holder.variant.getVantageName());
        holder.Quantity.setText("Weight : "+holder.variant.getVantageSize());
        holder.Price.setText(holder.variant.getVantagePrice());
        holder.Count.setText("x " + holder.variant.getVantageQty());
        holder.CountText.setText(String.valueOf(holder.ItemCount));
        holder.Count.setText(" x " + holder.variant.getVantageQty() + " = ");
        String value = holder.variant.getVantagePrice();
        //value = value.substring(1);
        //String result = value.replaceAll("[.,]","");
        holder.TotalAmount.setText(String.valueOf(Float.parseFloat(value) * ((float) holder.variant.getVantageQty())));
        Picasso.with(StartActivity.context).load(holder.variant.getVantageImage()).placeholder((int) R.mipmap.mystore).into(holder.Image);
        holder.lytItemMinus.setOnClickListener(new C05071(holder));
        holder.lytItemPlus.setOnClickListener(new C05082(holder));
        return view;
    }
}
