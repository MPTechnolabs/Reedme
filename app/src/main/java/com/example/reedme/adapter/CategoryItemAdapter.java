package com.example.reedme.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.activity.CategoryItemFullActivity;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.activity.StoreDiaplyActivity;
import com.example.reedme.fragments.CategoryItemActivity;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryItem1;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.CheckOutVantage;
import com.example.reedme.model.Product;
import com.example.reedme.model.Vantage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryItemAdapter extends BaseAdapter {
    CheckOutVantage chkVantage;
    List<Product> productList;
    Vantage vantage;
    ArrayList<Product> arraylist;

    CheckOutData checkOutData;

    /* renamed from: com.vegies.app.adapter.CategoryItemAdapter.1 */
    class C05041 implements OnClickListener {
        private final /* synthetic */ ViewHolder val$holder;

        C05041(ViewHolder viewHolder) {
            this.val$holder = viewHolder;
        }

        public void onClick(View v) {
            if (this.val$holder.ItemCount > 0) {
                ViewHolder viewHolder = this.val$holder;
                viewHolder.ItemCount--;
                this.val$holder.CountText.setText(String.valueOf(this.val$holder.ItemCount));
                Util.getInstance(StartActivity.context).RemoveCheckOutVantage(this.val$holder.vantage.getVantageId());
                CategoryItemActivity.getInstance().SetCheckOutValue();
            }

        }
    }
    class C05052 implements OnClickListener {
        private final /* synthetic */ ViewHolder val$holder;

        C05052(ViewHolder viewHolder) {
            this.val$holder = viewHolder;
        }

        public void onClick(View v) {
            ViewHolder viewHolder = this.val$holder;
            viewHolder.ItemCount++;
            this.val$holder.CountText.setText(String.valueOf(this.val$holder.ItemCount));
            Util.getInstance(StartActivity.context).AddCheckOutVantage(this.val$holder.vantage, this.val$holder.product.getProductName());
            CategoryItemActivity.getInstance().SetCheckOutValue();

            //AppPrefs.getAppPrefs(StartActivity.context).setString("storename", this.val$holder.product.getStorename());


        }
    }

    class C05063 implements OnClickListener {
        private final /* synthetic */ ViewHolder val$holder;

        C05063(ViewHolder viewHolder) {
            this.val$holder = viewHolder;
        }

        public void onClick(View v) {
            Product product = this.val$holder.product;
            Intent intent = new Intent(StartActivity.context, CategoryItemFullActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Product", product);
            intent.putExtras(bundle);
            StartActivity.context.startActivity(intent);
        }
    }

    static class ViewHolder {
        TextView CountText;
        ImageView Image;
        int ItemCount;
        TextView More;
        TextView Name;
        TextView OldPrice;
        TextView Price;
        TextView Quantity;
        TextView Size;
        ImageButton btnItemMinus;
        ImageButton btnItemPlus;
        RelativeLayout lytItemMinus;
        RelativeLayout lytItemPlus;
        Product product;
        Vantage vantage;

        ViewHolder() {
        }
    }

    public CategoryItemAdapter(List<Product> listItems) {

        this.productList = listItems;
        arraylist = new ArrayList<Product>();
        arraylist.addAll(productList);
    }

    public int getCount() {
        return this.productList.size();
    }

    public Object getItem(int position) {
        return this.productList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(StartActivity.context).inflate(R.layout.category_sub_item, null);
            holder = new ViewHolder();
            holder.Image = (ImageView) view.findViewById(R.id.img_category_sub_item);
            holder.Name = (TextView) view.findViewById(R.id.txt_category_sub_item_name);
            holder.Size = (TextView) view.findViewById(R.id.txt_category_sub_item_size);
            holder.Price = (TextView) view.findViewById(R.id.txt_category_sub_item_price);
            holder.CountText = (TextView) view.findViewById(R.id.txt_category_sub_item_add_count);
            holder.btnItemMinus = (ImageButton) view.findViewById(R.id.btn_category_sub_item_minus);
            holder.btnItemPlus = (ImageButton) view.findViewById(R.id.btn_category_sub_item_plus);
            holder.lytItemMinus = (RelativeLayout) view.findViewById(R.id.lyt_category_sub_item_minus);
            holder.lytItemPlus = (RelativeLayout) view.findViewById(R.id.lyt_category_sub_item_plus);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.ItemCount = 0;


        holder.product = this.productList.get(position);
        holder.vantage = this.productList.get(position).getVantages().get(0);
        this.chkVantage = Util.getInstance(StartActivity.context).getCheckoutAddedVantage(holder.vantage.getVantageId());
        if (this.chkVantage != null) {
            holder.ItemCount = this.chkVantage.getVantageQty();
        }

        this.checkOutData = AppPrefs.getAppPrefs(StartActivity.context).getCheckOutVantage();


        holder.CountText.setText(String.valueOf(holder.ItemCount));
        holder.Name.setText(holder.product.getProductName());
        holder.Size.setText("Weight : "+holder.vantage.getVantageSize());

        holder.Price.setText(holder.vantage.getVantagePrice());
     if (holder.vantage.getVantageImage().length() > 0) {
            Picasso.with(StartActivity.context).load(holder.vantage.getVantageImage()).placeholder((int) R.mipmap.logo).into(holder.Image);
        }
        if(Constants.flag == 0)
        {
           // Toast.makeText(StartActivity.context,""+String.valueOf(Util.getInstance(StartActivity.context).getCheckOutVantageCount(checkOutData)),Toast.LENGTH_LONG).show();
            holder.CountText.setVisibility(View.GONE);
            holder.lytItemPlus.setVisibility(View.GONE);
            holder.lytItemMinus.setVisibility(View.GONE);

        }
        else {

            holder.CountText.setTextColor(StartActivity.context.getResources().getColor(R.color.black));
            holder.lytItemPlus.setVisibility(View.VISIBLE);
            holder.lytItemMinus.setVisibility(View.VISIBLE);
            holder.lytItemMinus.setOnClickListener(new C05041(holder));
            holder.lytItemPlus.setOnClickListener(new C05052(holder));
        }

        view.setOnClickListener(new C05063(holder));
        return view;
    }
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        productList.clear();
        if (charText.length() == 0) {
            productList.addAll(arraylist);

        } else {
            for (Product postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.getProductName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    productList.add(postDetail);
                }

            }
        }
        notifyDataSetChanged();
    }
}
